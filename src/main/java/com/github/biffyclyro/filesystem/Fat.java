package com.github.biffyclyro.filesystem;

import com.github.biffyclyro.filesystem.Exeception.FatExption;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Fat {
    private List<Integer> fat;

    public Fat(int numBlocos) {
        this.fat = new ArrayList<>(numBlocos);
        for (int i = 0; i < numBlocos; i++) {
            this.fat.add(null);
        }
    }

    public List<Integer> alocarEspaco(int numBlocos) {

        List<Integer> livres = new ArrayList<>(numBlocos);

        for (int i = 0; i < fat.size(); i++) {
            if(fat.get(i) == null && numBlocos > 0){
                livres.add(i);
                numBlocos--;
            }
        }
        if ( numBlocos > 0) {
            throw new FatExption("Espaco insuficiente");
        } else {
            int livresLastIdx = livres.size()-1;

            fat.set(livres.get(livresLastIdx), livres.get(livresLastIdx));

            for (int i = livresLastIdx - 1; i >= 0; i--) {
                fat.set(livres.get(i), livres.get(i+1));
            }

            return livres;
        }
    }

    public int returnLivreCount(){
        AtomicInteger i = new AtomicInteger();

        fat.forEach(idx -> { if (idx == null ) i.getAndIncrement(); });

        return i.get();
    }
}
