package com.github.biffyclyro.filesystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class Diretorio {
    private byte[] nome = new byte[7];
    private int blocoInicial;
    private List<Arquivo> arquivos;

   /* public com.github.biffyclyro.filesystem.Diretorio(byte[] diretorio) {
        this.diretorio = diretorio;
        var dir = "1diretorio/0";
        var fat = "0fat";



    }
*/
    public Diretorio(byte[] nome, int blocoInicial) {
        this.nome = nome;
        this.blocoInicial = blocoInicial;
    }


    public byte[] getBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bytes.write(nome, 0, nome.length);
        bytes.write(Utils.intToByteArray(blocoInicial), 0, 4);
        if (arquivos != null) {
            arquivos.forEach(arq -> {
                try {
                    bytes.write(arq.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return bytes.toByteArray();
    }




    Diretorio(byte[] bytes) {
        byte[] bInicial = new byte[4];

        System.arraycopy(bytes, 0, this.nome, 0, Limits.NOME_LENGTH);
        System.arraycopy(bytes, Limits.NOME_LENGTH, bInicial, 0, 4);

        this.blocoInicial =  ByteBuffer.wrap(bInicial).getInt();

        for (int i = Limits.NOME_LENGTH + 4; i <= bytes.length; i += 18 ) {
            arquivos.add(new Arquivo(Arrays.copyOfRange(bytes, i , i+18)));
        }
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
