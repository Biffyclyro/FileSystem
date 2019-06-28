import java.io.FileNotFoundException;

public class TestCase {


    public static void main(String[] args) throws FileNotFoundException {
        Fat32 fat32 = new Fat32("disco1.fat32");
        String str = "aaaa";
        fat32.create("qualquer",  str.getBytes() );
        byte[] response = fat32.read("qualquer", 2, 19);

        String aaa = new String(response);


        System.out.println(aaa);



    }



}
