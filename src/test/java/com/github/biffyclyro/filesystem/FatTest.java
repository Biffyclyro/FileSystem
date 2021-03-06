package com.github.biffyclyro.filesystem;

import com.github.biffyclyro.filesystem.exeception.FatExeption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FatTest {
    private final int NUMBLOCOS = 10;
    private Fat       fat;

    @BeforeEach
    void setUp() {
        this.fat = new Fat(this.NUMBLOCOS);
    }

    @Test
    void alocarEspaco() {
        List esperado = Collections.singletonList(2);
        List blocos = this.fat.alocarEspaco(1);
        assertIterableEquals(esperado, blocos);

        esperado = IntStream.range(3,10).boxed().collect(Collectors.toList());
        blocos = this.fat.alocarEspaco(7);
        assertIterableEquals(esperado, blocos);

        assertThrows(FatExeption.class, () -> this.fat.alocarEspaco(1));
    }

    @Test
    void returnLivreCount() {
        assertEquals(8, this.fat.returnLivreCount());

        this.alocarEspaco();
        assertEquals(0, this.fat.returnLivreCount());
    }

    @Test
    void getBytes() {
        byte[] currentFat = this.fat.getBytes();
        Fat newFat = Fat.fatBuilder(this.fat.getBytes(), NUMBLOCOS);

        assertArrayEquals(currentFat, newFat.getBytes());
    }

    @Test
    void getBlocos() {
        List<Integer> blocosAlocados = this.fat.alocarEspaco(1);
        List<Integer> blocosRetornados = this.fat.getBlocos(2);
        assertIterableEquals(blocosAlocados, blocosRetornados);

        blocosAlocados = this.fat.alocarEspaco(7);
        blocosRetornados = this.fat.getBlocos(3);
        assertIterableEquals(blocosAlocados, blocosRetornados);
    }
}
