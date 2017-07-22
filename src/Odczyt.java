import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Odczyt{
    private Scanner in;
    private File file;

    public Odczyt(String plik) throws FileNotFoundException {
        file = new File(plik);
        in = new Scanner(file);
    }
    public String pobierzDana() {
        String zmienna = in.nextLine();
        //System.out.println(zmienna);
        return  zmienna;
    }
}