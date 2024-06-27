package com.example.hangmangame;

import android.content.Context;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

public class WordSelector {

    private List<String> easyWords;
    private List<String> mediumWords;
    private List<String> hardWords;
    private int level;

    public WordSelector(Context context, int level) {
        this.level = level;
        // Load words from your files here
         easyWords = GenerateRandomWord.readWordsFromAssets(context, "EASY.txt");
         mediumWords = GenerateRandomWord.readWordsFromAssets(context,"medium.txt");
         hardWords = GenerateRandomWord.readWordsFromAssets(context,"hard.txt");
    }

    public String getWordForLevel() {
        List<String> candidates;
        int minLength;
        int maxLength;
        int minRepetition;

        if (level <= 33) {
            candidates = easyWords;
            minLength = 4;
            maxLength = 6;

        } else if (level <= 66) {
            candidates = mediumWords;
            minLength = 7;
            maxLength = 9;

        } else {
            candidates = hardWords;
            minLength = 10;
            maxLength = 12; // Adjust this based on your word file characteristics

        }

        List<String> filteredWords = candidates.stream()
                .filter(word -> word.length() >= minLength && word.length() <= maxLength)
                .collect(Collectors.toList());

        if (filteredWords.isEmpty()) {
            // If no words match the criteria, fall back to any word in the difficulty level
            return GenerateRandomWord.getRandomWord(candidates);
        } else {
            return GenerateRandomWord.getRandomWord(filteredWords);
        }
    }



}

