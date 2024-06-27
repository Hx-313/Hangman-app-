package com.example.hangmangame;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

public class TriesLeft {
    void setTries(int tries, boolean[] guessed, ImageView triesSpace, String Message, TextView[] textViewArray , String[] letter){
        if(tries == 1) {
            triesSpace.setImageResource(R.drawable.two);
        }
        if(tries ==2) {
            triesSpace.setImageResource (R.drawable.three);
        }
        if(tries == 3) {
            triesSpace.setImageResource(R.drawable.four);
        }
        if(tries == 4) {
            triesSpace.setImageResource(R.drawable.five);
        }
        if(tries == 5) {
            triesSpace.setImageResource(R.drawable.six);
        }
        if(tries == 6) {
            triesSpace.setImageResource(R.drawable.seven);

        }


    }

}
