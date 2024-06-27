package com.example.hangmangame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityCompetition extends AppCompatActivity {
EditText word;
Handler handler;
String words;
String cWords;
EditText cWord;
EditText name;
TextView result;
ImageButton submit;
Button tryAgain;
    TextView playerName;
    TextView levl;
    TextView iq;
String regex = "^[a-zA-Z]+$";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_competition);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        playerName = findViewById(R.id.playerName);
        playerName.setText(String.valueOf(GameState.getPlayerName()));
        levl = findViewById(R.id.level);
        levl.setText(String.valueOf(levl.getText()) + String.valueOf(GameState.getPlayerLevel()));
        iq = findViewById(R.id.iq);
        iq.setText(String.valueOf(iq.getText()) + String.valueOf(GameState.getPlayerIQ()));

        TextView textView= findViewById(R.id.clickme);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityCompetition.this, 100); // Vibrate for 100 milliseconds

                Intent intent = new Intent(ActivityCompetition.this, ActivityProfileSetting.class);
                startActivity(intent);            }
        });
        word = findViewById(R.id.word);
        cWord = findViewById(R.id.Confirm_word);
        submit = findViewById(R.id.Submit);

        String whitespace = " ";
        View alertCustomDiaologue = LayoutInflater.from(ActivityCompetition.this).inflate(R.layout.state_dialogue,null);
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(ActivityCompetition.this);
        alertDialogue.setView(alertCustomDiaologue);
        final AlertDialog dialog = alertDialogue.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        result =alertCustomDiaologue.findViewById(R.id.result);
        tryAgain = alertCustomDiaologue.findViewById(R.id.tryAgain);
        tryAgain.setText("Try Again...");
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        handler = new Handler();

        submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityCompetition.this, 100); // Vibrate for 100 milliseconds

                words = String.valueOf(word.getText());
                cWords = String.valueOf(cWord.getText());
                if(words.contains(whitespace)){
                    result.setText("White spaces not allowed");
                    result.setTextColor(getResources().getColor(R.color.RED));
                    dialog.show();
                    word.setText("");
                    cWord.setText("");

                }else if(!words.matches(regex)) {
                    result.setText("Only English alphabets allowed");
                    result.setTextColor(getResources().getColor(R.color.RED));
                    dialog.show();
                    word.setText("");
                    cWord.setText("");
                }else if(words.length()!= cWords.length() ||!(words.equals(cWords)) ){
                    result.setText("Words Dont Match!!");
                    result.setTextColor(getResources().getColor(R.color.RED));
                    dialog.show();
                    word.setText("");
                    cWord.setText("");
                }
                else if(words.equals(cWords)){
                    result.setText("Starting game...");
                    Toast.makeText(ActivityCompetition.this,"Starting game...",Toast.LENGTH_SHORT).show();
                    result.setTextColor(getResources().getColor(R.color.green));
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           Intent intent = new Intent(ActivityCompetition.this,ActivityMainGame.class);
                           intent.putExtra("v1",words);
                           intent.putExtra("v2", "Competition Mode");
                           startActivity(intent);
                           word.setText("");
                           cWord.setText("");
                            }
                   },3000);
                }
            }
        });
    }
}