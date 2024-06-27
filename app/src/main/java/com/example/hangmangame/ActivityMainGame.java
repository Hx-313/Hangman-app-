package com.example.hangmangame;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityMainGame extends AppCompatActivity {
    TextView[] textViewArray;
    TextView banner;
    String message;
    String Message2;
    String[] letters , winCheck;
    LinearLayout layout;
    int totalAvailableWidth ;
    int numTextViews ;
    int textViewWidth;
    GridLayout keyboard;
    boolean[] guessed;
    boolean check =false;
    Adapter adapter;

    ImageView triesSpace;
    int tries =0;
    TriesLeft triesLeft;
    boolean[] buttonUsed;
    Button[] buttonsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
         message = intent.getStringExtra("v1");
         Message2 =  intent.getStringExtra("v2");
         letters = new String[message.length()];
         winCheck = new String[message.length()];
        banner = findViewById(R.id.banner);
        banner.setText(Message2);

        for (int i = 0; i < message.length(); i++) {
            letters[i] = String.valueOf(message.charAt(i));
        }
         layout = findViewById(R.id.guessArea);
        textViewArray = new TextView[message.length()];
        buttonsArray = new Button[27];

        createTextViews();
        triesLeft = new TriesLeft();
        createKeyboard(message,letters,textViewArray);




    }

    @SuppressLint("ResourceAsColor")
    private void createKeyboard(String message, String[] letter, TextView[] textViewArray) {
        // Initialize your layout
        keyboard = findViewById(R.id.keyboard);
        if (keyboard == null) {
            throw new IllegalStateException("Keyboard view is not found in the layout.");
        }

        // Initialize your arrays
        buttonsArray = new Button[27];
        winCheck = new String[message.length()]; // Assuming winCheck should match the length of the message
        guessed = new boolean[message.length()];
        buttonUsed = new boolean[27];

        layout.post(new Runnable() {
            @Override
            public void run() {
                totalAvailableWidth = keyboard.getWidth();
                int numColumns = 9; // Assuming you want 9 columns
                int buttonMargin = 10; // Left and right margins for each button
                int totalMarginSpace = numColumns * 2 * buttonMargin;
                textViewWidth = (totalAvailableWidth - totalMarginSpace) / numColumns; // Adjust for margins

                tries = 0;

                String[] kb = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
                Arrays.fill(guessed, false);

                int k = 0;
                for (int i = 0; i < 27; i++) {
                    Button button = new Button(ActivityMainGame.this);
                    buttonsArray[i] = button;

                    if (i == 26) {
                        button.setActivated(false);
                    } else {
                        button.setText(kb[k]);
                        k++;
                        button.setGravity(Gravity.CENTER);
                        button.setBackgroundColor(Color.BLACK);
                        button.setTextColor(Color.WHITE);
                        int finalK = k - 1;
                        int finalI = i;
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                VibratorUtil.vibrate(ActivityMainGame.this, 100); // Vibrate for 100 milliseconds

                                triesSpace = findViewById(R.id.triesImage);
                                if (triesSpace == null) {
                                    throw new IllegalStateException("Tries space view is not found in the layout.");
                                }
                                buttonUsed[finalI] = true;
                                boolean check = false;
                                for (int j = 0; j < letter.length; j++) {
                                    if (kb[finalK].equals(letter[j])) {
                                        textViewArray[j].setText(letter[j]);
                                        winCheck[j] = letter[j];
                                        textViewArray[j].setTextColor(Color.GREEN);
                                        guessed[j] = true;
                                        button.setBackgroundColor(Color.GREEN);
                                        button.setActivated(false);
                                        check = true;
                                    }
                                }
                                if (!check) {
                                    button.setBackgroundColor(Color.RED);
                                    button.setActivated(false);
                                    tries++;
                                    triesLeft.setTries(tries, guessed, triesSpace, message, textViewArray, letter);
                                }
                                if (tries == 6) {
                                    showDialog(message, Message2, tries, "Hard Luck ", "You Failed to Guess");
                                    for (int i = 0; i < 27; i++) {
                                        if (!buttonUsed[i]) {
                                            buttonsArray[i].setBackgroundColor(Color.RED);
                                            buttonsArray[i].setActivated(false);
                                        }
                                    }
                                }
                                if (Arrays.equals(winCheck, letter)) {
                                    showDialog(message, Message2, tries, "Congratulations", "You Guessed Right");
                                    SharedPreferences pref = getSharedPreferences(GameState.getPlayerId(), MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    if (Message2.contains("Adventure")) {
                                        int playerLevel = GameState.getPlayerLevel();
                                        playerLevel++;
                                        GameState.setPlayerLevel(playerLevel);
                                        editor.putInt("playerLevel", playerLevel);
                                    }
                                    if (Message2.equals("Rookie Mode")) {
                                        float playerIQ = GameState.getPlayerIQ();
                                        playerIQ += 0.2F;
                                        GameState.setPlayerIQ(playerIQ);
                                        editor.putFloat("playerIQ", playerIQ);
                                    }
                                    if (Message2.equals("Master Mode")) {
                                        float playerIQ = GameState.getPlayerIQ();
                                        playerIQ += 0.4F;
                                        GameState.setPlayerIQ(playerIQ);
                                        editor.putFloat("playerIQ", playerIQ);
                                    }
                                    if (Message2.equals("Veteran Mode")) {
                                        float playerIQ = GameState.getPlayerIQ();
                                        playerIQ += 0.4F;
                                        GameState.setPlayerIQ(playerIQ);
                                        editor.putFloat("playerIQ", playerIQ);
                                    }
                                    editor.apply();
                                    for (int i = 0; i < 27; i++) {
                                        if (!buttonUsed[i]) {
                                            buttonsArray[i].setBackgroundColor(Color.GREEN);
                                            buttonsArray[i].setActivated(false);
                                        }
                                    }
                                }
                            }
                        });
                    }

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.setMargins(buttonMargin, 60, buttonMargin, 20);
                    params.width = textViewWidth; // Set the width calculated earlier
                    button.setLayoutParams(params);

                    keyboard.addView(button);
                }
            }
        });
    }



    private void createTextViews() {
        // Measure width of parent container
        layout.post(new Runnable() {
            @Override
            public void run() {
                totalAvailableWidth = layout.getWidth();
                numTextViews = letters.length;
                textViewWidth = (totalAvailableWidth - (8*numTextViews)) /  numTextViews;
                 for (int i = 0; i < numTextViews; i++) {

                    TextView textView = new TextView(ActivityMainGame.this);
                    textViewArray[i] = textView;
                    textView.setText(" ");
                    textView.setId(i);
                    textView.setPadding(10,10,10,10);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(textViewWidth, WRAP_CONTENT);
                    params.gravity = Gravity.CENTER;
                    params.setMargins(0,8,8,8);
                    textView.setLayoutParams(params);
                    textView.setTextColor(Color.BLACK);
                    int textSize = calculateTextSize(textViewWidth, letters[i]);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundResource(R.drawable.bottom_border);
                    layout.addView(textView);

                }
            }
        });

    }

    private int calculateTextSize(int textViewWidth, String text) {
        // Choose an appropriate scaling factor
        final float scalingFactor = 0.3f;

        // Calculate maximum allowed text size based on width
        int maxSize = (int) (textViewWidth * scalingFactor);


        // Calculate the length of the text
        int textLength = text.length();

        // Calculate an initial text size
        int initialSize = textViewWidth / textLength;
         // Return the smaller of initialSize and maxSize
      if( initialSize < maxSize){
          return initialSize;
      }
      else {
          return maxSize;
      }
    }
    void showDialog(String message1,String message2,int triesLeft,String greetingMessage, String greetingMessage2 ){
        View alertCustomDiaologue =
                LayoutInflater.from(ActivityMainGame.this).
                        inflate(R.layout.game_result_dialog,null);
        AlertDialog.Builder alertDialogue =
                new AlertDialog.Builder(ActivityMainGame.this);
        alertDialogue.setView(alertCustomDiaologue);
        final AlertDialog dialog = alertDialogue.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView wordArea = alertCustomDiaologue.findViewById(R.id.wordArea);
        TextView triesArea = alertCustomDiaologue.findViewById(R.id.usedTries);
        TextView modeArea = alertCustomDiaologue.findViewById(R.id.modeArea);
        TextView Greeting = alertCustomDiaologue.findViewById(R.id.greeting);
        TextView Greeting2 = alertCustomDiaologue.findViewById(R.id.greeting2);
        ImageView image = alertCustomDiaologue.findViewById(R.id.setImage);
        if(tries==6) {
            image.setImageResource(R.drawable.sad);
        }else {
            image.setImageResource(R.drawable.partypopper);
        }
        wordArea.setText(message1);
        modeArea.setText(message2);
        Greeting.setText(greetingMessage);
        Greeting2.setText(greetingMessage2);
        triesArea.setText(String.valueOf(triesLeft));
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Button continu = alertCustomDiaologue.findViewById(R.id.btnfollow);
        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                    Intent intent = new Intent(ActivityMainGame.this,ActivityMenu.class);
                    startActivity(intent);
                    finish();

            }
        });
        Button again = alertCustomDiaologue.findViewById(R.id.btnTryAGain);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    finish();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensure menu music is playing when this activity resumes
        MusicUtil.changeMusic(this, R.raw.menu_music);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Change back to menu music when the game activity pauses
        MusicUtil.changeMusic(this, R.raw.menu_music);
    }


}

