import java.io.FileNotFoundException;
import java.util.Arrays;

public class TestCase {


    public static void main(String[] args) throws FileNotFoundException {
        Fat32 fat32 = new Fat32("disco1.fat32");
        fat32.escreveTabela(24);
        String exemplo = "tomara";
        String exemplo2 = "q de";

        fat32.append("Indice", exemplo.getBytes() );
        fat32.append("Indice", exemplo2.getBytes() );

        byte[] dsads = fat32.getTABLE();


        byte[] response = fat32.read("fat", 1, 2);

        int numero = response[0];


        System.out.println(numero);



    }



}
