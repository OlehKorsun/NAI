import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ReadFile readTrain = new ReadFile("iris_train.txt");
        readTrain.read();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Wprowadź alfa: ");
        double alfa = scanner.nextDouble();

        if(alfa < 0 || alfa > 1){
            throw new InputMismatchException();
        }

        System.out.println("Wprowadź próg: ");
        double threshold = scanner.nextDouble();

        int n = readTrain.getVektory().getFirst().getWspolbierzne().length;

        double[] weights = new double[n];

        for(int i = 0; i<weights.length; ++i){
            weights[i] = Math.random()*10;
            System.out.println(weights[i]);
        }

        Perceptron perceptron = new Perceptron(weights, threshold, alfa, n);

        perceptron.addActiveClass(readTrain.getVektory().getFirst().getKlass());   // teraz ta klasa będzie odpowiadała 1 na wyjściu

        double suma = 0;
        double accurancy = 0;
        
        while(accurancy<=95.0){
            accurancy=0;
            suma=0;
            for(Vektor vek : readTrain.getVektory()){
                int res = perceptron.computeTrening(vek);
                if(res == 1 && vek.getKlass().equals(perceptron.getActiveClass()) || res == 0 && !vek.getKlass().equals(perceptron.getActiveClass())){
                    suma++;
                }
                System.out.println("Vektor: " + vek + " klasyfikacja: " + res);
            }
            accurancy = (suma / readTrain.getVektory().size())*100;
        }

//        for(int i = 0; i<50; ++i){
//            for(Vektor vek : readTrain.getVektory()){
//                int res = perceptron.computeTrening(vek);
//                if(res == 1 && vek.getKlass().equals(perceptron.getActiveClass()) || res == 0 && !vek.getKlass().equals(perceptron.getActiveClass())){
//                    suma++;
//                }
//                System.out.println("Vektor: " + vek + " klasyfikacja: " + res);
//            }
//        }
//
//        accurancy = (suma / (readTrain.getVektory().size()*50))*100;

        System.out.println("Próg: " + perceptron.getThreshold());
        System.out.println("Wagi: " + Arrays.toString(perceptron.getWeights()));
        System.out.println("Accurancy: " + accurancy + "%");

        ReadFile readTest = new ReadFile("iris_test.txt");
        readTest.read();

        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-Część testowa-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

        suma = 0;

        for(Vektor vek : readTest.getVektory()){
            int res = perceptron.computeTest(vek);
            if(res == 1 && vek.getKlass().equals(perceptron.getActiveClass()) || res == 0 && !vek.getKlass().equals(perceptron.getActiveClass())){
                suma++;
            }
            System.out.println("Vektor: " + vek + " klasyfikacja: " + res);
        }

        accurancy = (suma / readTest.getVektory().size())*100;

        System.out.println("Próg: " + perceptron.getThreshold());
        System.out.println("Wagi: " + Arrays.toString(perceptron.getWeights()));
        System.out.println("Accurancy: " + accurancy + "%");

        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-Wprowadzenie wektora do klasyfikacji-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

        System.out.println("Wprowadź wektor do klasyfikacji:");
        String newClasification = scanner.next();

        while(!newClasification.equals("0")){

            Vektor newVektor = Vektor.create(newClasification, perceptron.getN());

            int res = perceptron.computeTest(newVektor);

            if(res == 1){
                System.out.println(perceptron.getActiveClass());
            } else {
                System.out.println("Inna klasa");
            }

            System.out.println("Wprowadź wektor do klasyfikacji:");
            newClasification = scanner.next();
        }
    }
}