package com.example.hangmangame;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityAdventure extends AppCompatActivity {
ImageButton Resume;
ImageButton newGame;
String word;
    TextView playerName;
    TextView levl;
    TextView iq;
    Button tryAgain;
    TextView result;
WordSelector wordSelector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adventure);
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
                Intent intent = new Intent(ActivityAdventure.this, ActivityProfileSetting.class);
                startActivity(intent);            }
        });
        Resume = findViewById(R.id.option1);
        newGame = findViewById(R.id.newGame);
        Resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityAdventure.this, 100); // Vibrate for 100 milliseconds

                wordSelector = new WordSelector(ActivityAdventure.this,(GameState.getPlayerLevel()));
                word = wordSelector.getWordForLevel();
                Intent intent = new Intent(ActivityAdventure.this,ActivityMainGame.class);
                intent.putExtra("v1",word);
                Toast.makeText(ActivityAdventure.this,word,Toast.LENGTH_SHORT).show();
                intent.putExtra("v2","Adventure Level "+GameState.getPlayerLevel() );
                startActivity(intent);
            }
        });
        View alertCustomDiaologue = LayoutInflater.from(ActivityAdventure.this).inflate(R.layout.state_dialogue,null);
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(ActivityAdventure.this);
        alertDialogue.setView(alertCustomDiaologue);
        final AlertDialog dialog = alertDialogue.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        result =alertCustomDiaologue.findViewById(R.id.result);
        tryAgain = alertCustomDiaologue.findViewById(R.id.tryAgain);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityAdventure.this, 100); // Vibrate for 100 milliseconds
                result.setText("Your'r Level will be reset to zero");
                result.setTextColor(getResources().getColor(R.color.RED));
                dialog.show();
                //GameState.setPlayerLevel(0);
            }
        });


    }
}