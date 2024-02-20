package com.javaee.exercises.anagrams.model;

import java.io.Serializable;

public class Word implements Serializable {
    private String solution;
    private String anagram;

    public Word(String solution, String anagram) {
        this.solution = solution;
        this.anagram = anagram;
    }       

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getAnagram() {
        return anagram;
    }

    public void setAnagram(String anagram) {
        this.anagram = anagram;
    }
}
