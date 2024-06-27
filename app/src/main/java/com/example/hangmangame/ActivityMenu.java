package com.example.hangmangame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityMenu extends AppCompatActivity {
    ImageButton adv;
    private static final String PREF_NAME = "MyPrefs";
    ImageButton Ai;
    ImageButton comp;
    TextView playerName;
    TextView levl;
    TextView iq;
    private boolean doubleBackToExitPressedOnce = false;
    private static final int BACK_PRESS_DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MusicUtil.changeMusic(this, R.raw.menu_music);
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
                VibratorUtil.vibrate(ActivityMenu.this, 100); // Vibrate for 100 milliseconds

                Intent intent = new Intent(ActivityMenu.this, ActivityProfileSetting.class);
                startActivity(intent);
               }
        });
        adv = findViewById(R.id.adv);
        adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityMenu.this, 100); // Vibrate for 100 milliseconds

                Intent intent = new Intent(ActivityMenu.this,ActivityAdventure.class);
                startActivity(intent);
            }
        });
        Ai = findViewById(R.id.AI);
        Ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityMenu.this, 100); // Vibrate for 100 milliseconds

                Intent intent = new Intent(ActivityMenu.this,ActivityQuickMatch.class);
                startActivity(intent);
            }
        });
        comp = findViewById(R.id.comp);
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityMenu.this, 100); // Vibrate for 100 milliseconds
                Intent intent = new Intent(ActivityMenu.this,ActivityCompetition.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        playerName = findViewById(R.id.playerName);
        playerName.setText(String.valueOf(GameState.getPlayerName()));

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Ensure menu music is playing when this activity resumes
        MusicUtil.changeMusic(this, R.raw.menu_music);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity(); // Close the game
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, BACK_PRESS_DELAY);
    }

}