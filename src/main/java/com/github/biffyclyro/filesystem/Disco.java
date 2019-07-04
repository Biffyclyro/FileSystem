package com.github.biffyclyro.filesystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Disco {

    private RandomAccessFile file;
    private final int NUM_BLOCOS;
    private static final int TAMANHO_BLOCO = 64 * 1024;

    public Disco(String nomeArquivo, int numBlocos) throws FileNotFoundException {
        this.file = new RandomAccessFile(nomeArquivo, "rw");
        this.NUM_BLOCOS = numBlocos;
    }

    public byte[] readBlock(int numero) throws IOException {
        file.seek(numero * TAMANHO_BLOCO);
        byte[] bloco = new byte[TAMANHO_BLOCO];
        file.read(bloco);
        return bloco;
    }

    public void writeBlock(byte[] bytes, int numBloco) throws IOException {
        if (bytes.length > TAMANHO_BLOCO) {
            throw new IllegalArgumentException("tentando escrever bloco maior que o tamanho máximo.");
        }
        file.seek(numBloco * TAMANHO_BLOCO);
        file.write(bytes);
    }

    public int getTamanhoBloco(){
        return TAMANHO_BLOCO;
    }


}
