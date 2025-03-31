public class Perceptron {
    private double[] weights;
    private double threshold;
    private double alfa;
    private int n;
    private String activeClass;   // Ta klasa odpowiada 1, pozostałe to są 0

    public Perceptron(double[] weights, double threshold, double alpha, int n) {
        this.weights = weights;
        this.threshold = threshold;
        this.alfa = alpha;
        this.n = n;
    }

    public int computeTrening(Vektor x){
        int y = 0;
        if( skalar(x)>=threshold){
            y = 1;
        }

        if(y == 0 && x.getKlass().equals(activeClass)){           // y=0 d=1
            learn(x.getWspolbierzne(), y, 1);
        } else if(y == 1 && !x.getKlass().equals(activeClass)){   // y=1 d=0
            learn(x.getWspolbierzne(), y, 0);
        }
        return y;
    }

    public int computeTest(Vektor x){
        int y = 0;
        if( skalar(x)>=threshold){
            y = 1;
        }
        return y;
    }

    public double skalar(Vektor x){
        double suma = 0;
        for(int i = 0; i < x.getWspolbierzne().length; i++){
            suma+=x.getWspolbierzne()[i]*weights[i];
        }
        return suma;
    }

    public void learn(double[] x, int y, int d){

        threshold = threshold-(d-y)*alfa;
        double[] tmp = multiplyAlfaAndX(y, d, x);

        for(int i = 0; i < n; i++){
            weights[i] += tmp[i];
        }
    }

    public double[] multiplyAlfaAndX(int y, int d, double[] x){
        double[] tmp = new double[n];
        for(int i = 0; i < n; i++){
            tmp[i]+=x[i]*alfa*(d-y);
        }
        return tmp;
    }

    public void addActiveClass(String str){
        this.activeClass=str;
    }

    public String getActiveClass(){
        return activeClass;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getThreshold() {
        return threshold;
    }

    public double getAlfa() {
        return alfa;
    }

    public int getN() {
        return n;
    }

}