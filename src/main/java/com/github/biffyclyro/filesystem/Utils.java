package com.github.biffyclyro.filesystem;

public class Utils {

    public static byte[] intToByteArray(int i) {
        byte[] convertido = new byte[4];

        convertido[0] = (byte) (i >>> 24);
        convertido[1] = (byte) (i >>> 16);
        convertido[2] = (byte) (i >>>  8);
        convertido[3] = (byte)  i;

        return convertido;
    }

    public static int byteArrayToInt(byte[] bytes) {
        return (bytes[0] << 24)
                + ((bytes[1] & 0xFF) << 16)
                + ((bytes[2] & 0xFF) << 8)
                + ( bytes[3] & 0xFF);
    }
}
