package com.example.hangmangame;
import static java.security.AccessController.getContext;

import android.content.Context;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aHafi
 */
public class EasyWord extends Words{
    private String word;
    public EasyWord(Context context){
        setWord( context);
    }
    @Override
    public void setWord(Context context) {
        String path = "EASY.txt";


            word = GenerateRandomWord.RandomWord(context, path);


    }
    @Override
    public String getWord() {

        return word;
    }
}
