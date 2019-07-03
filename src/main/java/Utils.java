public class Utils {

    public static byte[] intToByteArray(int i) {
        byte[] data = new byte[4];
        data[3] = (byte) (i & 0xFF);
        data[2] = (byte) ((i >> 8) & 0xFF);
        data[1] = (byte) ((i >> 16) & 0xFF);
        data[0] = (byte) ((i >> 24) & 0xFF);
        return data;
    }

}
