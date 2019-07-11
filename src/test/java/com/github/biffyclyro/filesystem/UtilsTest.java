package com.github.biffyclyro.filesystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void intToByteArrayAndByteArrayToInt() {
        int x = 1;
        byte[] bytes = Utils.intToByteArray(1);
        assertEquals(1, Utils.byteArrayToInt(bytes));
    }
}
