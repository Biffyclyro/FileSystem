import java.util.ArrayList;

public class Fat {
    private final byte[] table;

    public Fat(byte[] table) {
        this.table = table;
    }

    public byte[] getTable() {
        return table;
    }

    public ArrayList<Integer> escreveFat(int tamanhoArquivo){

        ArrayList<Integer> livres = new ArrayList<>();

        for (int index: table){
            if(index != 0) livres.add(index);
        }

        if(livres.size() >= tamanhoArquivo){
            for (int livre: livres){
                if(livres.get(livres.indexOf(livre) + 1).byteValue() == livres.size()){
                    table[livre] = (byte) livre;
                }else {
                    table[livre] = livres.get(livres.indexOf(livre) + 1).byteValue();
                }
            }
        }

        return livres;
    }

    public int returnLivre(){
        int i = 0;

        for (int index: table){
            if(index != 0) i++;
        }

        return i;

    }








}
