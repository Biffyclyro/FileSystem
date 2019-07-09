package com.github.biffyclyro.filesystem;

import com.github.biffyclyro.filesystem.Exeception.FatExption;

import java.io.ByteArrayOutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Fat {
    private List<Integer> fat;

    public Fat(int numBlocos) {
        this.fat = new ArrayList<>(numBlocos);
        this.fat.add(0);
        for (int i = 0; i < numBlocos-1; i++) {
            this.fat.add(-1);
        }
        this.fat.set(0, 0); // Dir raiz no bloco 0
        this.fat.set(1, 0); // Fat no bloco 1
    }

    public Fat(byte[] bytes, int numBlocos) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte[] headerBytes = new byte[3];
        buffer.get(headerBytes, 0, 3);
        String header = new String(headerBytes);

        if ( header.equals("fat") ) {
            this.fat = new ArrayList<>(numBlocos);
            try {
                while ( buffer.hasRemaining() ) {
                    int i = buffer.getInt();
                    this.fat.add(i);
                }
            } catch ( BufferUnderflowException ignored ) {

            }
        }
    }

    public List<Integer> alocarEspaco(int numBlocos) {

        List<Integer> livres = new ArrayList<>(numBlocos);

        for (int i = 0; i < fat.size() && numBlocos > 0; i++) {
            if ( fat.get(i) == -1 ) {
                livres.add(i);
                numBlocos--;
            }
        }
        if ( numBlocos > 0) {
            throw new FatExption("Espaco insuficiente");
        } else {
            int livresLastIdx = livres.size()-1;

            fat.set(livres.get(livresLastIdx), 0);

            for (int i = livresLastIdx - 1; i >= 0; i--) {
                fat.set(livres.get(i), livres.get(i+1));
            }

            return livres;
        }
    }

    public byte[] getBytes() {
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();

        outBuffer.writeBytes("fat".getBytes());
        this.fat.forEach(idx ->
                outBuffer.writeBytes(ByteBuffer.allocate(4).putInt(idx).array()));

        return outBuffer.toByteArray();
    }

    public List<Integer> getBlocos(int blocoInicial) {
        int bloco = blocoInicial;
        List<Integer> blocos = new LinkedList<>();

        blocos.add(bloco);
        while ( this.fat.get(bloco) != 0 ) {
            bloco = this.fat.get(bloco);
            blocos.add(bloco);
        }
        return blocos;
    }

    public void removerEntrada(int blocoInicial) {
        this.getBlocos(blocoInicial).forEach(idx ->
                this.fat.set(idx, -1));
    }

    public int returnLivreCount(){
        AtomicInteger i = new AtomicInteger();

        fat.forEach(idx -> { if (idx == -1 ) i.getAndIncrement(); });

        return i.get();
    }
}
