package com.github.biffyclyro.filesystem.diretorio;

import com.github.biffyclyro.filesystem.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Arquivo extends EntradaDiretorio {
    public Arquivo(String nome, int tamanho, int blocoInicial, boolean isDiretorio) {
        setNome(nome);
        this.tamanho      = tamanho;
        this.blocoInicial = blocoInicial;
        this.isDiretorio  = isDiretorio;
    }

    public Arquivo(byte[] bytes) throws IOException {
        final ByteArrayInputStream byteInStream = new ByteArrayInputStream(bytes);

        this.isDiretorio  = byteInStream.read() != 0;
        this.nome         = new String(byteInStream.readNBytes(11));
        this.tamanho      = Utils.byteArrayToInt(byteInStream.readNBytes(4));
        this.blocoInicial = Utils.byteArrayToInt(byteInStream.readNBytes(4));
    }

    @Override
    boolean validarNome(String nome) {
        if ( super.validarNome(nome) ) {
            String[] nomePartido = nome.split("\\.");

            return nomePartido.length == 2
                    && nomePartido[0].length() <= 7
                    && nomePartido[1].length() <= 3;
        }
        return false;
    }
}
