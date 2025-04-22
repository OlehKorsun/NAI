import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String[]> data = new ArrayList<>();
        String path = "train.txt";
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                data.add(line.split(","));
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] to_classify = {"night","happy","friends","sad"};
        System.out.println(bayes(data,to_classify));
    }
    public static String bayes(ArrayList<String[]> data, String[] to_classify){


        Map<String, Double> kolumnaDecyzyjna = new HashMap<>();
        Map<String, Double> mapaPrawdopodobienstw = new HashMap<>();

        int ogolnaIloscWierszy = data.size();
        int iloscKolumn = to_classify.length;

        for (int i = 0; i < data.size(); i++) {
            kolumnaDecyzyjna.put(data.get(i)[data.get(i).length-1], kolumnaDecyzyjna.getOrDefault(data.get(i)[data.get(i).length-1], 0.)+1);
        }

        // dla każdej klasy
        for(Map.Entry<String, Double> entry : kolumnaDecyzyjna.entrySet()){

            ArrayList<Double> listaPrawdopodobienstwDlaKazdegoAtrybutu = new ArrayList<>();


            //dla każdej kolumny ze zbioru to_clasify
            for(int j = 0; j<iloscKolumn; ++j){
                double iloscWystopienDanegoAtrybutuWTablicy = 0;



                //przejście po każdym wierszu
                for (int k = 0; k < ogolnaIloscWierszy; k++) {
                    if(data.get(k)[j].equals(to_classify[j]) && data.get(k)[iloscKolumn].equals(entry.getKey())){
                        iloscWystopienDanegoAtrybutuWTablicy++;
                    }
                }
                if(iloscWystopienDanegoAtrybutuWTablicy!=0){

                    double prawdopodobienstwo = iloscWystopienDanegoAtrybutuWTablicy / entry.getValue();
                    listaPrawdopodobienstwDlaKazdegoAtrybutu.add(prawdopodobienstwo);

                } else {

                    // Wygładzenie
                    Set<String> zbiorDlaWygladzenia = new HashSet<>();
                    for (int i = 0; i < ogolnaIloscWierszy; i++) {
                        zbiorDlaWygladzenia.add(data.get(i)[j]);
                    }

                    double prawdopodobienstwo = 1. / (zbiorDlaWygladzenia.size() + entry.getValue());
                    listaPrawdopodobienstwDlaKazdegoAtrybutu.add(prawdopodobienstwo);
                }
            }

            // obliczam prawdopodobienstwo dla każdego atrybutu decyzyjnego
            double res = entry.getValue() / ogolnaIloscWierszy;
            for(Double x : listaPrawdopodobienstwDlaKazdegoAtrybutu){
                res *= x;
            }
            mapaPrawdopodobienstw.put(entry.getKey(), res);

        }

        // szykam największe prawdopodobienstwo
        double max = 0;
        String res = "";
        for(Map.Entry<String, Double> e : mapaPrawdopodobienstw.entrySet() ){
            if(e.getValue() > max){
                res = e.getKey();
                max = e.getValue();
            }
            System.out.println("Atrybut: " + e.getKey() + " ma prawdopodobienstwo: " + e.getValue());
        }

        return res;
    }
}