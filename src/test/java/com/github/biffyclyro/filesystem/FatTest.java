package com.github.biffyclyro.filesystem;

import com.github.biffyclyro.filesystem.Exeception.FatExption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FatTest {
    private final int numBlocos = 10;
    private Fat fat;

    @BeforeEach
    void setUp() {
        this.fat = new Fat(this.numBlocos);
    }

    @Test
    void alocarEspaco() {
        List esperado = Collections.singletonList(0);
        List blocos = this.fat.alocarEspaco(1);
        assertIterableEquals(esperado, blocos);

        esperado = IntStream.range(1,10).boxed().collect(Collectors.toList());
        blocos = this.fat.alocarEspaco(9);
        assertIterableEquals(esperado, blocos);

        assertThrows(FatExption.class, () -> this.fat.alocarEspaco(1));
    }

    @Test
    void returnLivreCount() {
        assertEquals(10, this.fat.returnLivreCount());

        this.alocarEspaco();
        assertEquals(0, this.fat.returnLivreCount());
    }
}
