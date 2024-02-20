package com.javaee.exercises.anagrams.bean;

import com.javaee.exercises.anagrams.model.Word;
import com.javaee.exercises.anagrams.qualifier.Anagram;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class WordGenerator {

    @Produces
    @Anagram(Anagram.Language.ENGLISH)
    public Word createAnagram() {
        List<String> wordList = new ArrayList<>();
        wordList.add("house");
        wordList.add("garden");
        wordList.add("doorway");
        wordList.add("window");
        return shuffle(wordList);
    }

    @Produces
    @Anagram(Anagram.Language.FRENCH)
    public Word createFrenchAnagram() {
        List<String> wordList = new ArrayList<>();
        wordList.add("maison");
        wordList.add("jardin");
        wordList.add("porte");
        wordList.add("garage");
        return shuffle(wordList);
    }

    public Word shuffle(List<String> wordList) {
        Random rand = new Random();
        int index = rand.nextInt(wordList.size());
        String word = wordList.get(index);
        List<Character> characters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(word.length());
        while (!characters.isEmpty()) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        System.out.println("Secret word is " + word);
        return new Word(word, output.toString());
    }
}
