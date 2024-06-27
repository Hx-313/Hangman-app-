package com.example.hangmangame;

import android.content.Intent;
import android.os.Bundle;
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

public class ActivityQuickMatch extends AppCompatActivity {
ImageButton easy;
EasyWord ew;
MediumWord med;
hardWord hrd;
ImageButton medium;
ImageButton hard;
    TextView playerName;
    TextView levl;
    TextView iq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quick_match);
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
                VibratorUtil.vibrate(ActivityQuickMatch.this, 100); // Vibrate for 100 milliseconds

                Intent intent = new Intent(ActivityQuickMatch.this, ActivityProfileSetting.class);
                startActivity(intent);            }
        });
        easy = findViewById(R.id.easy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityQuickMatch.this, 100); // Vibrate for 100 milliseconds

                ew = new EasyWord(getApplicationContext());
                Intent intent = new Intent(ActivityQuickMatch.this, ActivityMainGame.class);
                intent.putExtra("v1",ew.getWord());
                intent.putExtra("v2","Rookie Mode");
                Toast.makeText(ActivityQuickMatch.this,ew.getWord(),Toast.LENGTH_LONG).show();
                startActivityForResult(intent,101);
            }
        });

        medium = findViewById(R.id.medium);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityQuickMatch.this, 100); // Vibrate for 100 milliseconds

                med = new MediumWord(getApplicationContext());
                Intent intent = new Intent(ActivityQuickMatch.this, ActivityMainGame.class);
                intent.putExtra("v1",med.getWord());
                intent.putExtra("v2","Master Mode");
                Toast.makeText(ActivityQuickMatch.this,med.getWord(),Toast.LENGTH_LONG).show();
                startActivityForResult(intent,101);
            }
        });

        hard = findViewById(R.id.hard);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityQuickMatch.this, 100); // Vibrate for 100 milliseconds

                hrd = new hardWord(getApplicationContext());
                Intent intent = new Intent(ActivityQuickMatch.this, ActivityMainGame.class);
                intent.putExtra("v1",hrd.getWord());
                intent.putExtra("v2","Veteran Mode");
                Toast.makeText(ActivityQuickMatch.this, hrd.getWord(),Toast.LENGTH_LONG).show();
                startActivityForResult(intent,101);
            }
        });
    }
}