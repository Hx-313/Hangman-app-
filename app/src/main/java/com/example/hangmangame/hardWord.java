package com.example.hangmangame;

import android.content.Context;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aHafi
 */
public class hardWord extends Words {
    private String word;
    hardWord(Context context){
        setWord(context);
    }
    @Override
    public void setWord(Context context) {
        String path = "HARD.txt";

        word = GenerateRandomWord.RandomWord(context,path);

    }
    @Override
    public String getWord() {
        return word;
    }

}

