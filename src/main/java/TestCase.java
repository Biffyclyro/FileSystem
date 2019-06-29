import java.io.FileNotFoundException;
import java.util.Arrays;

public class TestCase {


    public static void main(String[] args) throws FileNotFoundException {
        Fat32 fat32 = new Fat32("disco1.fat32");
        fat32.escreveTabela(24);

        byte[] dsads = fat32.getTABLE();
        int pau = dsads[0];

        byte[] response = fat32.read("fat", 0, 19);

        String aaa = new String();


        System.out.println(pau);



    }



}
