package com.github.biffyclyro.filesystem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Fat32Test {
    private Fat32 fat32 = null ;

    @BeforeEach
    void setUp() throws IOException {
        this.fat32 = new Fat32("discoTeste");
    }

    @AfterEach
    void cleanUp () {
        File disco = new File("discoTeste");
        disco.delete();
    }

    @Test
    void create() {
        assertDoesNotThrow(() -> this.fat32.create("teste.txt", "teste123sdasd".getBytes()));

        String multiBlocos = "1".repeat(Disco.TAMANHO_BLOCO) +
                "2".repeat(Disco.TAMANHO_BLOCO);

        assertDoesNotThrow(() ->
                this.fat32.create("teste2.txt", multiBlocos.getBytes())); // asserção escrevendo dois blocos
    }

    @Test
    void read() {
        String dado = "dadoTeste";
        this.fat32.create("teste3.txt", dado.getBytes());
        assertEquals(dado, new String(this.fat32.read("teste3.txt",0, dado.length())));

        String multiBlocos = "1".repeat(Disco.TAMANHO_BLOCO) +
                "2".repeat(Disco.TAMANHO_BLOCO);

        this.fat32.create("teste2.txt", multiBlocos.getBytes());
        assertEquals(multiBlocos, new String(this.fat32.read("teste2.txt",0, -1)));
    }

    @Test
    void append() {
        String bloco1 = "1".repeat(Disco.TAMANHO_BLOCO +1);
        String bloco2 = "2".repeat(Disco.TAMANHO_BLOCO -1);

        this.fat32.create("tAppend.txt", bloco1.getBytes());
        this.fat32.append("tAppend.txt", bloco2.getBytes());

        StringBuilder tudo = new StringBuilder(bloco1);
        tudo.append(bloco2);

        assertEquals(tudo.toString(),
                new String(this.fat32.read("tAppend.txt", 0, tudo.toString().length())));
    }

    @Test
    void remove() {
        this.create();
        this.fat32.remove("teste.txt");
        assertArrayEquals(new byte[0], this.fat32.read("teste.txt",0, 10));
    }

    @Test
    void freeSpace() {
        // -2 blocos(fat e diretorio root)
        assertEquals(Disco.TAMANHO_BLOCO * (Fat32.NUM_BLOCOS - 2) , this.fat32.freeSpace());
    }

    @Test
    void listFiles() {
        assertEquals("", this.fat32.listFiles());

        this.fat32.create("teste.txt", "1".getBytes());
        assertEquals("teste.txt" + '\n', this.fat32.listFiles());
    }

    @Test
    void createDir() {
        this.fat32.createDir("teste");
        assertEquals("teste/"+'\n', this.fat32.listFiles());
    }

    @Test
    void persistencia() throws IOException {
        String dado = "abc";

        this.fat32.create("teste.abc", dado.getBytes());

        String teste = new String(new Fat32("discoTeste").read("teste.abc", 0, -1));

        assertEquals(dado, teste);
    }

    @Test
    void createWithDir() {
        this.fat32.createDir("teste");
        assertDoesNotThrow(() -> this.fat32.createWithDir("teste/teste.txt", "testezinho".getBytes()));
    }

    @Test
    void readWithDir() {
        String dado = "teste";

        String dado2 = "teste".repeat(Disco.TAMANHO_BLOCO * 2);

        this.fat32.createDir("teste");
        this.fat32.createWithDir("teste/teste.txt", dado.getBytes());

        this.fat32.createWithDir("teste/teste2.txt", dado2.getBytes());

        assertEquals(dado, new String(this.fat32.readWithDir("teste/teste.txt", 0, -1)));
        assertEquals(dado2, new String(this.fat32.readWithDir("teste/teste2.txt", 0, -1)));
    }
}
