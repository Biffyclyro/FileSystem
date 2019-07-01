import java.io.FileNotFoundException;
import java.io.IOException;

public class Fat32 implements FileSystem {
    private static final int NUM_BLOCOS = 10000;
    private Disco disco;
    private Fat fat;


    public Fat32(String fileName) throws FileNotFoundException {

        this.disco = new Disco(fileName, NUM_BLOCOS);

        if(read("TabelaFat", 1, 2) == null){

            this.fat = new Fat(new byte[NUM_BLOCOS]);
            fat.getTable()[1] = 0;

            try {
                disco.writeBlock(fat.getTable(), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {

            this.fat = new Fat( read("TabelaFat", 1, 2) );

        }

    }







    @Override
    public void create(String fileName, byte[] data) {
        var tamanho = data.length;

        try {
            disco.writeBlock(data, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void append(String fileName, byte[] data) {
        byte[] bloco = null;
        try{
           bloco = disco.readBlock(0);
        }catch (Exception e){
            e.printStackTrace();
        }

        int i = 0;
        int j = 0;
        byte[] apendice = new byte[disco.getTamanhoBloco()];


            for(int bytes : bloco){

                if(bytes == 0 && j < data.length){
                    apendice[i] = data[j];
                    j++;

                } else {
                    apendice[i] = (byte) bytes;
                }
                i++;

            }

       // System.out.println(i);


        try {
            disco.writeBlock(apendice, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public byte[] read(String fileName, int offset, int limit) {


        try {
            byte[] retorno = disco.readBlock(offset);
            return retorno;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new byte[0];

    }

    @Override
    public void remove(String fileName) {

    }

    @Override
    public int freeSpace() {
        return 0;
    }

    @Override
    public String listFiles() {
        return null;
    }
}
