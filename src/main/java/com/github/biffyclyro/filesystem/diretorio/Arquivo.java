package com.github.biffyclyro.filesystem.diretorio;

import com.github.biffyclyro.filesystem.Limits;
import com.github.biffyclyro.filesystem.Utils;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@Getter
public class Arquivo extends EntradaDiretorio {

    private String nome;
    private int size;
    private int blocoInicial;
    public final boolean isDiretorio;


    public Arquivo(String nome, int size, int blocoInicial, boolean isDiretorio) {
        setNome(nome);
        this.size         = size;
        this.blocoInicial = blocoInicial;
        this.isDiretorio  = isDiretorio;
    }

    public Arquivo(byte[] bytes) throws IOException {
        final ByteArrayInputStream byteInStream = new ByteArrayInputStream(bytes);

        this.isDiretorio = byteInStream.read() != 0;
        this.nome = new String(byteInStream.readNBytes(11));
        this.size = Utils.byteArrayToInt(byteInStream.readNBytes(4));
        this.blocoInicial = Utils.byteArrayToInt(byteInStream.readNBytes(4));
    }


    public void setNome(String nome) {
        if ( validarNomeArquivo(nome) ) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException("Nome de arquivo invalido");
        }
    }

    public byte[] getBytes() {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();

        byteOutStream.write(isDiretorio?1:0);
        byteOutStream.write(ByteBuffer.allocate(11).put(this.nome.getBytes()).array(), 0, 11);
        byteOutStream.write(Utils.intToByteArray(size), 0, 4);
        byteOutStream.write(Utils.intToByteArray(blocoInicial), 0, 4);

        return byteOutStream.toByteArray();
    }

    private boolean validarNomeArquivo(String nomeArquivo) {
        if( nomeArquivo.length() > 11) {
            return false;
        } else {
            String[] nomePartido = nomeArquivo.split("\\.");

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
}
