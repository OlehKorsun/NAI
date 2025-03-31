public class Vektor {
    private double[] wspolbierzne;
    private String klass;

    public Vektor(double[] tabl, String klass){
        this.wspolbierzne=tabl;
        this.klass=klass;
    }

    public double odleglosc(double[] tabl){

        if(wspolbierzne.length!=tabl.length)
            throw new ArithmeticException();

        double res = 0;

        for(int i=0; i<tabl.length; ++i){
            res += (wspolbierzne[i]-tabl[i])*(wspolbierzne[i]-tabl[i]);
        }
        return Math.sqrt(res);
    }

    public double[] getWspolbierzne(){
        return wspolbierzne;
    }
    public String getKlass(){
        return klass;
    }

    public static Vektor create(String str, int iloscWspolrzednych){
        String[] tmp = str.split(",");
        double[] tabl = new double[iloscWspolrzednych];
        if(tabl.length!=iloscWspolrzednych){
            throw new ArithmeticException();
        }
        for(int i = 0; i<tmp.length; ++i){
            tabl[i] = Double.parseDouble(tmp[i]);
        }
        return new Vektor(tabl, null);
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Vector: ");
        for(double i : wspolbierzne){
            stringBuilder.append(i + " ");
        }
        stringBuilder.append("Klas: " + klass);
        return stringBuilder.toString();
    }
}
