package com.example.hangmangame;

import android.content.Context;
import android.view.ContextThemeWrapper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aHafi
 */
public class MediumWord extends Words {
    private String word;
    MediumWord(Context context){
        setWord(context);
    }
    @Override
    public void setWord(Context context) {
        String path = "MEDIUM.txt";

        word = GenerateRandomWord.RandomWord(context,path);

    }
    @Override
    public String getWord() {
        return word;
    }

}

