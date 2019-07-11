package com.github.biffyclyro.filesystem;

import com.github.biffyclyro.filesystem.diretorio.Arquivo;
import com.github.biffyclyro.filesystem.diretorio.Diretorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fat32 implements FileSystem {
    public static final int NUM_BLOCOS = 10000;
    private Disco disco;
    private Fat fat;
    private Diretorio root;


    Fat32(String fileName) throws IOException {
        this.disco = new Disco(fileName, NUM_BLOCOS);

        try {
            this.root = new Diretorio(disco.readBlock(0));
        } catch ( IllegalArgumentException e ) {
            this.root = new Diretorio("/", 0);
        }

        this.fat = Fat.fatBuilder(disco.readBlock(1), NUM_BLOCOS);


        this.disco.writeBlock(root.toByteArray(), 0);
    }

    @Override
    public void create(String fileName, byte[] data) {
        int numBlocos = getNumBlocos(data);
        List<Integer> blocos = this.fat.alocarEspaco(numBlocos);

        Arquivo a = new Arquivo(fileName, data.length, blocos.get(0), false);

        this.root.addEntrada(a);

        List<byte[]> dados = splitByteArray(data);

        try {
            for ( int i = 0 ; i < numBlocos; i++ ) {
                disco.writeBlock(dados.get(i), blocos.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.update();
    }


    public void createDir(String dirName) {
        int bloco = this.fat.alocarEspaco(1).get(0);
        Diretorio root = new Diretorio(dirName, bloco);
        try {
            this.disco.writeBlock(root.toByteArray(), bloco);
            this.disco.writeBlock(fat.getBytes(), 1);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void append(String fileName, byte[] data) {
        try {
            Arquivo arquivo = this.root.getEntrada(fileName);
            if ( arquivo.isDiretorio() ) {
                return;
            }

            int espacoOcupadoUltBloco = arquivo.getTamanho() % Disco.TAMANHO_BLOCO;
            int espacoLivreUltBloco =  Disco.TAMANHO_BLOCO - espacoOcupadoUltBloco ;

            int tamAdicional =  data.length - espacoLivreUltBloco;
            int numBloco = getNumBlocos(data);

            List<Integer> blocos  = this.fat.append(arquivo.getBlocoInicial(), getNumBlocos(tamAdicional));

            arquivo.setTamanho(arquivo.getTamanho() + data.length);
            this.root.modEntrada(arquivo);

            List<byte[]> dados ;
            int offset = 0;

            if ( espacoOcupadoUltBloco != 0 ) {
                ByteArrayOutputStream bOut = new ByteArrayOutputStream();

                bOut.write(disco.readBlock(blocos.get(0)), 0, espacoOcupadoUltBloco);
                bOut.write(data);

                dados = splitByteArray(bOut.toByteArray());
            } else {
                dados = splitByteArray(data);
                offset = 1;
            }

            for ( int i = 0; i < numBloco; i++ ) {
                disco.writeBlock(dados.get(i), blocos.get(i + offset));
            }

            this.update();

        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] read(String fileName, int offset, int limit) {
        try {
            Arquivo a = this.root.getEntrada(fileName);

            List<Integer> blocos = this.fat.getBlocos(a.getBlocoInicial());
            ByteBuffer buffer = ByteBuffer.allocate(getNumBlocos(a.getTamanho()) * Disco.TAMANHO_BLOCO + 1 );

            blocos.forEach(b -> {
                try {
                    buffer.put(disco.readBlock(b));
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            });

            ByteArrayInputStream bOut = new ByteArrayInputStream(buffer.array(), offset, limit);

            return bOut.readAllBytes();
        } catch ( IOException ex ) {
            return new byte[0];
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            this.root.rmEntrada(fileName);
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public int freeSpace() {
        return fat.returnLivreCount()*disco.getTamanhoBloco();
    }

    @Override
    public String listFiles() {
        return this.root.list();
    }

    private void update() {
        try {
            disco.writeBlock(this.root.toByteArray(), 0);
            disco.writeBlock(this.fat.getBytes(), 1);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private static int getNumBlocos(byte[] bytes) {
        return getNumBlocos(bytes.length);
    }

    private static int getNumBlocos(int length) {
        return (int) Math.ceil((double) length / Disco.TAMANHO_BLOCO) ;
    }

    private static List<byte[]> splitByteArray(byte[] dados) {
        int numBlocos = getNumBlocos(dados);

        ByteBuffer buffer = ByteBuffer.allocate(Disco.TAMANHO_BLOCO * numBlocos).put(dados);

        List<byte[]> l = new ArrayList<>(numBlocos);

        int inicio = 0;

        for ( int i = 0; i < numBlocos; i++ ) {
            inicio = Disco.TAMANHO_BLOCO * i;
            if(inicio + Disco.TAMANHO_BLOCO > dados.length) {
                l.add(Arrays.copyOfRange(dados, inicio, dados.length));
            } else {
                l.add(Arrays.copyOfRange(dados, inicio, inicio + Disco.TAMANHO_BLOCO));
            }
        }
        return l;
    }
}
