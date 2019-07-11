package com.github.biffyclyro.filesystem.diretorio;

import com.github.biffyclyro.filesystem.Utils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public abstract class EntradaDiretorio {
    String nome;
    boolean isDiretorio;
    int tamanho;
    int blocoInicial;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if ( validarNome(nome) ) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException("Nome invalido");
        }
    }

    public boolean isDiretorio() {
        return isDiretorio;
    }

    public void setDiretorio(boolean diretorio) {
        isDiretorio = diretorio;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getBlocoInicial() {
        return blocoInicial;
    }

    public void setBlocoInicial(int blocoInicial) {
        this.blocoInicial = blocoInicial;
    }

    boolean validarNome(String nome) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(nome)
                && nome.length() <= 11;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();

        byteOutStream.write(isDiretorio?1:0);
        byteOutStream.write(ByteBuffer.allocate(11).put(this.nome.getBytes()).array(), 0, 11);
        byteOutStream.write(Utils.intToByteArray(tamanho), 0, 4);
        byteOutStream.write(Utils.intToByteArray(blocoInicial), 0, 4);

        return byteOutStream.toByteArray();
    }
}
