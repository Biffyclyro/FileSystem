package com.github.biffyclyro.filesystem;

import com.github.biffyclyro.filesystem.exeception.FatExeption;

import java.io.ByteArrayOutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Fat {
    private List<Integer> fat;


    public static Fat fatBuilder(byte[] bytes, int numBlocos) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        byte[] headerBytes = new byte[3];
        byteBuffer.get(headerBytes, 0, 3);
        String header = new String(headerBytes);

        if ( header.equals("fat") ) {
            return new Fat(byteBuffer, numBlocos);
        } else {
            return new Fat(numBlocos);
        }
    }

    public Fat(int numBlocos) {
        this.fat = new ArrayList<>(numBlocos);
        this.fat.add(0);
        for (int i = 0; i < numBlocos-1; i++) {
            this.fat.add(-1);
        }
        this.fat.set(0, 0); // Dir raiz no bloco 0
        this.fat.set(1, 0); // Fat no bloco 1
    }

    private Fat(ByteBuffer byteBuffer, int numBlocos) {
        this.fat = new ArrayList<>(numBlocos);
        try {
            while ( byteBuffer.hasRemaining() ) {
                int i = byteBuffer.getInt();
                this.fat.add(i);
            }
        } catch ( BufferUnderflowException ignored ) {
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
            throw new FatExeption("Espaco insuficiente");
        } else {
            return ligarBlocos(livres);
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

    public List<Integer> append(int blocoInicial, int numBlocosAdicionais) {
        List<Integer> blocos = this.getBlocos(blocoInicial);
        int tamInicial = blocos.size();

        if ( numBlocosAdicionais > 0  ) {
            blocos.addAll(this.alocarEspaco(numBlocosAdicionais));
            ligarBlocos(blocos);
        }

        return blocos.subList(tamInicial - 1, blocos.size());
    }

    private List<Integer> ligarBlocos(List<Integer> lista) {
        int ultimoIdx = lista.size()-1;

        fat.set(lista.get(ultimoIdx), 0);

        for (int i = ultimoIdx - 1; i >= 0; i--) {
            fat.set(lista.get(i), lista.get(i+1));
        }

        return lista;
    }
}
