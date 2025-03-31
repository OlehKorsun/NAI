import java.util.*;

public class Klasyfikator {
    private ReadFile readFile;
    public Klasyfikator(ReadFile readFile){
        this.readFile=readFile;
    }


    public String klasyfikuj(Vektor vektor, int k){

        List<Map<Double, Vektor>> odleglosci = new ArrayList<>();

        for(Vektor vek : readFile.getVektory()){
            double odleglosc = vektor.odleglosc(vek.getWspolbierzne());
            HashMap<Double, Vektor> tmp = new HashMap<>();
            tmp.put(odleglosc, vek);
            odleglosci.add(tmp);
        }

        quickSort(odleglosci, 0, odleglosci.size()-1);

        Map<String, Integer> map = new HashMap<>();

        for(int i = 0; i<k; ++i){
            String klasa = odleglosci.get(i).values().iterator().next().getKlass();
            map.put(klasa, map.getOrDefault(klasa, 0)+1);
        }

        String najczestrzaKlasa = null;
        int max = 0;

        for(Map.Entry<String, Integer> entry : map.entrySet()){
            if(entry.getValue() > max){
                max = entry.getValue();
                najczestrzaKlasa = entry.getKey();
            }
        }
        return najczestrzaKlasa;
    }

    private void quickSort(List<Map<Double, Vektor>> list, int begin, int end){
        if(begin < end){
            int partitionIndex = partition(list, begin, end);
            quickSort(list, begin, partitionIndex-1);
            quickSort(list, partitionIndex+1, end);
        }
    }


    private int partition(List<Map<Double, Vektor>> list, int begin, int end){
        double pivot = list.get(end).keySet().iterator().next();
        int i = begin-1;

        for(int j = begin; j<end; ++j){
            double currentPivot = list.get(j).keySet().iterator().next();

            if(currentPivot <= pivot){
                i++;
                mySwap(list, i, j);
            }
        }
        mySwap(list, i+1, end);
        return i+1;
    }

    private static void mySwap(List<Map<Double, Vektor>> list, int i, int j){
        if (i < 0 || i >= list.size() || j < 0 || j >= list.size()) {
            throw new IndexOutOfBoundsException();
        }
        Map<Double, Vektor> tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }
}
