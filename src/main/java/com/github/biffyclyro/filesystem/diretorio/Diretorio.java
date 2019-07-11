package com.github.biffyclyro.filesystem.diretorio;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class Diretorio extends EntradaDiretorio {
    private List<Arquivo> arquivos = new LinkedList<>();

    public Diretorio(String nome, int blocoInicial) {
        this.nome = nome;
        this.isDiretorio = true;
        this.blocoInicial = blocoInicial;
    }

    public byte[] getBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bytes.write(isDiretorio ? 1 : 0);
        try {
            bytes.write(ByteBuffer.allocate(8).put(this.nome.getBytes(StandardCharsets.US_ASCII)).array());
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        bytes.write(ByteBuffer.allocate(4).putInt(blocoInicial).array(), 0, 4);

        if (arquivos != null) {
            arquivos.forEach(arq -> {
                try {
                    bytes.write(arq.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return bytes.toByteArray();
    }

    public void addEntrada(Arquivo a) {
        this.arquivos.add(a);
    }

    public String list() {
        StringBuilder str = new StringBuilder();

        arquivos.forEach( idx -> {
            str.append(idx.getNome());
            if ( idx.isDiretorio ) str.append("/");
            str.append('\n');
        });

        return str.toString();
    }

    public void rmEntrada(String nome) throws FileNotFoundException {
        this.arquivos.remove(this.getEntrada(nome));
    }

    public Arquivo getEntrada(String nome) throws FileNotFoundException {
        for ( Arquivo a : this.arquivos ) {
            if ( a.getNome().equals(nome) ) {
                return a;
            }
        }
        throw new FileNotFoundException("Arquivo com nome: " + nome + " nao encontrado");
    }

//    Diretorio(byte[] bytes) {
//        byte[] bInicial = new byte[4];
//
//        System.arraycopy(bytes, 0, this.nome, 0, Limits.NOME_LENGTH);
//        System.arraycopy(bytes, Limits.NOME_LENGTH, bInicial, 0, 4);
//
//        this.blocoInicial =  ByteBuffer.wrap(bInicial).getInt();
//
//        for (int i = Limits.NOME_LENGTH + 4; i <= bytes.length; i += 18 ) {
//            arquivos.add(new Arquivo(Arrays.copyOfRange(bytes, i , i+18)));
//        }
//    }
}
