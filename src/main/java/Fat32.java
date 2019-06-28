import java.io.FileNotFoundException;
import java.io.IOException;

public class Fat32 implements FileSystem{
    private static final int NUM_BLOCOS = 10000;
    private Disco disco;

    public Fat32(String fileName) throws FileNotFoundException {
        this.disco = new Disco(fileName, NUM_BLOCOS);
    }

    @Override
    public void create(String fileName, byte[] data) {

        try {
            disco.writeBlock(data, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void append(String fileName, byte[] data) {

    }

    @Override
    public byte[] read(String fileName, int offset, int limit) {


        try{
           byte[] retorno = disco.readBlock(offset);
            return retorno;

        }catch (Exception e){
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