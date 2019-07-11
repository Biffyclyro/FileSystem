package com.github.biffyclyro.filesystem;

import com.github.biffyclyro.filesystem.diretorio.Diretorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiretorioTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getBytes() {
        Diretorio d = new Diretorio("teste", 0);
        byte[] bytes = d.getBytes();

        assertEquals(d.getNome(), new Diretorio(bytes).getNome());
    }
}
