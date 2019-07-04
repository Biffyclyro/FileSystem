package com.github.biffyclyro.filesystem;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestCase {


    public static void main(String[] args) throws FileNotFoundException {
/*        com.github.biffyclyro.filesystem.Fat32 fat32 = new com.github.biffyclyro.filesystem.Fat32("disco1.fat32");

        String exemplo = "tomara";
        String exemplo2 = "q de";
        fat32.create("aaa",exemplo.getBytes());
        fat32.create("aaa",exemplo2.getBytes());
        fat32.append("Indice", exemplo.getBytes() );
        fat32.append("Indice", exemplo2.getBytes() );*/
        Arquivo arquivo = new Arquivo("rola", "doc", 12, 123);
        Diretorio diretorio = new Diretorio("diretorio".getBytes(), 1);



        // byte[] response = fat32.read("fat", 1, 2);

        // int numero = response[0];

        var disco = new Disco("disco", 12123123);
  /*      try {
            disco.writeBlock(arquivo.getBytes(), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        com.github.biffyclyro.filesystem.Arquivo arq = null;
        try {
            arq = new com.github.biffyclyro.filesystem.Arquivo(disco.readBlock(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new String(arq.getNome()).trim() + new String(arq.getExt()).trim());

    }*/
        try {
            disco.writeBlock(diretorio.getBytes(), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
