package com.example.hangmangame;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateRandomWord {

    public static String RandomWord(Context context, String fileName) {
        List<String> wordList = readWordsFromAssets(context, fileName);

        if (!wordList.isEmpty()) {
            String randomWord = getRandomWord(wordList);
            return randomWord;
        } else {
            return "File was not read";
        }
    }

    protected static List<String> readWordsFromAssets(Context context, String fileName) {
        List<String> words = new ArrayList<>();

        try {
            // Open the file from assets
            AssetManager assetManager = context.getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));

            // Read each line from the file
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim());
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return words;
    }

    protected static String getRandomWord(List<String> wordList) {
        Random random = new Random();
        int randomIndex = random.nextInt(wordList.size());
        return wordList.get(randomIndex);
    }
}
