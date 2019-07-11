package com.github.biffyclyro.filesystem.diretorio;

import com.github.biffyclyro.filesystem.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class Diretorio extends EntradaDiretorio {
    private List<EntradaDiretorio> arquivos = new LinkedList<>();

    public Diretorio(String nome, int blocoInicial) {
        this.nome = nome;
        this.isDiretorio = true;
        this.blocoInicial = blocoInicial;
        this.tamanho = 0;
    }

    public Diretorio(byte[] bytes) {
        ByteArrayInputStream bIn = new ByteArrayInputStream(bytes);

        if ( bIn.read() != 0 ) {
            this.isDiretorio = true;
        } else {
            throw new IllegalArgumentException("Dado não é um diretorio");
        }

        try {
            this.nome         = new String(bIn.readNBytes(11)).trim();
            this.tamanho      = Utils.byteArrayToInt(bIn.readNBytes(4));
            this.blocoInicial = Utils.byteArrayToInt(bIn.readNBytes(4));

            for ( int i = tamanho; i > 0; i-- ) {
                this.arquivos.add(new Arquivo(bIn.readNBytes(20)));
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bytes.write(isDiretorio ? 1 : 0);
        try {
            bytes.write(ByteBuffer.allocate(11).put(this.nome.getBytes()).array());
            bytes.write(Utils.intToByteArray(tamanho));
            bytes.write(Utils.intToByteArray(blocoInicial));
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        if ( arquivos.size() > 0 ) {
            arquivos.forEach(arq -> {
                try {
                    bytes.write(arq.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return bytes.toByteArray();
    }

    public void addEntrada(EntradaDiretorio ed) {
        try {
            getEntrada(ed.getNome());
            throw new IllegalArgumentException(ed.getNome() + " ja existe");
        } catch ( FileNotFoundException e ) {
            this.arquivos.add(ed);
            this.tamanho++;
        }
    }

    public void modEntrada(EntradaDiretorio e) throws FileNotFoundException {
        int idx = this.arquivos.indexOf(this.getEntrada(e.getNome()));

        this.arquivos.set(idx, e);
    }

    public String list() {
        StringBuilder str = new StringBuilder();

        arquivos.forEach( idx -> {
            str.append(idx.getNome());
            if ( idx.isDiretorio ) {
                str.append("/");
            }
            str.append('\n');
        });

        return str.toString();
    }

    public void rmEntrada(String nome) throws FileNotFoundException {
        if ( this.arquivos.remove(this.getEntrada(nome)) ) {
            this.tamanho--;
        }
    }

    public EntradaDiretorio getEntrada(String nome) throws FileNotFoundException {
        for ( EntradaDiretorio e : this.arquivos ) {
            if ( e.getNome().equals(nome) ) {
                return e;
            }
        }
        throw new FileNotFoundException("Arquivo com nome: " + nome + " nao encontrado");
    }
}
