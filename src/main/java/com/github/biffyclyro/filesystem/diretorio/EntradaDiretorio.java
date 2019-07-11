package com.github.biffyclyro.filesystem.diretorio;

import java.io.IOException;

public abstract class EntradaDiretorio {
    String nome;
    boolean isDiretorio;
    int blocoInicial;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if ( nome.length() > 8 ) {
            throw new IllegalArgumentException("Nome do arquivo maior que 8 caracteres");
        }
        this.nome = nome;
    }

    protected abstract byte[] getBytes();
}
