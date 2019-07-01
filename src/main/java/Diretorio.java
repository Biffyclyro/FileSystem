public class Diretorio {
    private final byte[] diretorio;

    public Diretorio(byte[] diretorio) {
        this.diretorio = diretorio;
        var dir = "1diretorio/";
        var fat = "0fat";



    }

    public byte[] escreveDiretorio(String nomeArquivo, int blocoInicial){

        int i = 0, j = 0;

        for(byte espaco : diretorio){
            if(espaco == 0 && j < nomeArquivo.getBytes().length){
                espaco = nomeArquivo.getBytes()[j];
                j++;

            }



/*
            else {
                nomeArquivo.getBytes()[i] = (byte) espaco;
            }*/

        }

        return diretorio;

    }

    private boolean validarNomeArquivo(String nomeArquivo) {
        if( nomeArquivo.length() > 11) {
            return false;
        } else {
            String[] nomePartido = nomeArquivo.split(".");

            if ( nomePartido.length == 2 ) {
                if ( nomePartido[0].length() <= 7 ) {
                    if ( nomePartido[1].length() <= 3 ) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public int leDiretorio(){
        return 0;
    }



}
