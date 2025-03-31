import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadFile {
    private String plik;

    private ArrayList<Vektor> vektory;

    public ReadFile(String plik){
        this.plik=plik;
        vektory = new ArrayList<>();
    }

    public String getPlik(){
        return plik;
    }

    public List<Vektor> getVektory(){
        return vektory;
    }

    public void read() {
        try {
            List<String> list = Files.readAllLines(Paths.get(plik));
            for (String i : list) {
                String[] str = i.split(",");
                double[] tmp = new double[str.length-1];
                for (int j = 0; j < tmp.length; ++j) {
                    tmp[j] = Double.parseDouble(str[j]);
                }
                vektory.add(new Vektor(tmp, str[str.length-1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.shuffle(vektory);
    }
}