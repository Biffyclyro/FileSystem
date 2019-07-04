package com.github.biffyclyro.filesystem;

import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

@Data
public class Arquivo {

    private byte[] nome = new byte[Limits.NOME_LENGTH];
    private byte[] ext = new byte[Limits.EXT_LENGTH];
    private int size;
    private int blocoInicial;

    public Arquivo(String nome, String ext, int size, int blocoInicial) {
        System.arraycopy(nome.getBytes(), 0, this.nome, 0, nome.length());
        System.arraycopy(ext.getBytes(), 0, this.ext, 0, ext.length());
        this.size = size;
        this.blocoInicial = blocoInicial;
    }

    public byte[] getBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bytes.write(1);
            bytes.write(nome, 0, Limits.NOME_LENGTH);
            bytes.write(Utils.intToByteArray(size), 0, 4);
            bytes.write(Utils.intToByteArray(blocoInicial), 0, 4);

        return bytes.toByteArray();
    }



    Arquivo(byte[] bytes) {
        byte[] sz = new byte[4];
        byte[] bInicial = new byte[4];

        System.arraycopy(bytes, 0, this.nome, 0, Limits.NOME_LENGTH);
        System.arraycopy(bytes, Limits.NOME_LENGTH , this.ext, 0, Limits.EXT_LENGTH);
        System.arraycopy(bytes, Limits.NOME_LENGTH + Limits.EXT_LENGTH , sz, 0, 4);
        System.arraycopy(bytes, Limits.NOME_LENGTH + Limits.EXT_LENGTH + 4, bInicial, 0, 4);

        this.size =  ByteBuffer.wrap(sz).getInt();
        this.blocoInicial =  ByteBuffer.wrap(bInicial).getInt();
    }

}
