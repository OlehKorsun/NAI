import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Wprowadź parametr k: ");
        int k = scanner.nextInt();

        ReadFile readTrain = new ReadFile("iris_train.txt");
        readTrain.read();

        Klasyfikator klasyfikator = new Klasyfikator(readTrain);

        ReadFile readTest = new ReadFile("iris_test.txt");
        readTest.read();


        int totalNumber = readTest.getVektory().size();
        double proper = 0;

        for(Vektor i : readTest.getVektory()){
            System.out.println(i);
            System.out.println(klasyfikator.klasyfikuj(i, k));

            if(i.getKlass().equals(klasyfikator.klasyfikuj(i, k))){
                proper++;
            }
        }

        double dokladnosc = (proper / totalNumber) * 100;
        System.out.println("Dokładność = " + dokladnosc+"%");

        int iloscWspolrzednych = readTrain.getVektory().getFirst().getWspolbierzne().length;

        System.out.println("Wprowadź wektor do klasyfikacji:");
        String newClasification = scanner.next();

        while(!newClasification.equals("0")){

            Vektor newVektor = Vektor.create(newClasification, iloscWspolrzednych);

            if(newVektor.getKlass() == null){
                System.out.println(klasyfikator.klasyfikuj(newVektor, k));
            } else {
                String klass = "";
                klass = klasyfikator.klasyfikuj(newVektor, k);
                System.out.println(klasyfikator.klasyfikuj(newVektor, k));
                if(klass.equals(newVektor.getKlass())){
                    System.out.println("Prawidłowo");
                } else {
                    System.out.println("Nieprawidłowo!");
                }
            }
            System.out.println("Wprowadź wektor do klasyfikacji:");
            newClasification = scanner.next();
        }
    }
}