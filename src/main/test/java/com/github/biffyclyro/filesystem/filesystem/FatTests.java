package com.github.biffyclyro.filesystem.filesystem;

import com.github.biffyclyro.filesystem.Fat;

public class FatTests {
    public static final Fat fat = new Fat(10);

    public static void main(String[] args) {
        testAlocacao();
    }

    private static void testAlocacao() {
        fat.alocarEspaco(1);
    }

}
