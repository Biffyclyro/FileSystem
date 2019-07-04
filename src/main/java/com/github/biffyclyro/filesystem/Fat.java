package com.github.biffyclyro.filesystem;

import com.github.biffyclyro.filesystem.Exeception.FatExption;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Fat {
    private List<Integer> fat;

    public Fat(int numBlocos) {
        this.fat = new ArrayList<>(numBlocos);
        for (int i = numBlocos; i >= 0; i--) {
            this.fat.add(null);
        }
    }

    public Optional<List<Integer>> alocarEspaco(int numBlocos) {

        List<Integer> livres = new LinkedList<>();

        for ( Integer idx : fat ) {
            if(idx == null && numBlocos > 0){
                livres.add(idx);
                numBlocos--;
            }
        }

        for ( Integer idx : livres) {
            livres.
        }

        if ( numBlocos > 0) {
            throw new FatExption("Espaco insuficiente");
        }

        return Optional.of(livres);
    }

    public int returnLivreCount(){
        AtomicInteger i = new AtomicInteger();

        fat.forEach(idx -> { if (idx == 0 ) i.getAndIncrement(); });

        return i.get();
    }
}
