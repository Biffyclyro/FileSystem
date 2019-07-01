import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Setter
public class Arquivo {
    private static final int NOME_LENGTH = 7;
    private static final int EXT_LENGTH = 3;

    private byte[] nome = new byte[NOME_LENGTH];
    private byte[] ext = new byte[EXT_LENGTH];
    private int size;
    private int blocoInicial;


    private Arquivo(String nome, String ext, int size) {
        System.arraycopy(nome.getBytes(), 0, this.nome, 0, nome.length);
        System.arraycopy(ext.getBytes(), 0, this.ext, 0, ext.length);
        this.size = size;
    }

    public byte[] getBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            bytes.write(nome);
            bytes.write(ext, NOME_LENGTH, EXT_LENGTH);
            bytes.write(intToByteArray(size), NOME_LENGTH+EXT_LENGTH, 4);
            bytes.write(intToByteArray(blocoInicial), NOME_LENGTH+EXT_LENGTH+4, 4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes.toByteArray();
    }

    private byte[] intToByteArray(int i) {
        byte[] data = new byte[4];
        data[3] = (byte) (i & 0xFF);
        data[2] = (byte) ((i >> 8) & 0xFF);
        data[1] = (byte) ((i >> 16) & 0xFF);
        data[0] = (byte) ((i >> 24) & 0xFF);
        return data;
    }

}
