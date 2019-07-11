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

        StringBuilder multiBlocos = new StringBuilder();
        multiBlocos.append("1".repeat(Disco.TAMANHO_BLOCO));
        multiBlocos.append("A".repeat(Disco.TAMANHO_BLOCO));

        assertDoesNotThrow(() ->
                this.fat32.create("teste2.txt", String.valueOf(multiBlocos).getBytes())); // asserção escrevendo dois blocos
    }

    @Test
    void read() {
        String dado = "dadoTeste";
        this.fat32.create("teste3.txt", dado.getBytes());
        assertEquals(dado, new String(this.fat32.read("teste3.txt",0, dado.length())));

        StringBuilder multiBlocos = new StringBuilder();
        multiBlocos.append("1".repeat(Disco.TAMANHO_BLOCO));
        multiBlocos.append("A".repeat(Disco.TAMANHO_BLOCO));
        this.fat32.create("teste2.txt", String.valueOf(multiBlocos).getBytes());
        assertEquals(multiBlocos.toString(), new String(this.fat32.read("teste2.txt",0, multiBlocos.length())));
    }

    @Test
    void createDir() {
        fail("Não implementado");
    }

    @Test
    void append() {
        fail("Não implementado");
    }

    @Test
    void remove() {
        this.create();
        System.out.println(this.fat32.listFiles());
        this.fat32.remove("teste.txt");
        assertArrayEquals(new byte[0], this.fat32.read("teste.txt",0, 10));
    }

    @Test
    void freeSpace() {
        fail("Não implementado");
    }

    @Test
    void listFiles() {
        assertEquals("", this.fat32.listFiles());

        this.fat32.create("teste.txt", "1".getBytes());
        assertEquals("teste.txt" + '\n', this.fat32.listFiles());
    }
}
