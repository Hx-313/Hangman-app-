package com.example.hangmangame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
Handler handler ;
    private boolean doubleBackToExitPressedOnce = false;
    private static final int BACK_PRESS_DELAY = 2000;
    private static final String KEY_GAME_STATE = "gameState";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences1 = getSharedPreferences("User1", MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("User2", MODE_PRIVATE);


        boolean isActive1  = preferences1.getBoolean("isActivePlayer" , true);
        boolean isActive2  = preferences2.getBoolean("isActivePlayer" , false);

        if(isActive1){
            GameState.setPlayerId("User1");
        }else if(isActive2){
            GameState.setPlayerId("User2");
        }
        // Check if it's the first time the app is launched
        SharedPreferences preferences = getSharedPreferences(GameState.getPlayerId(), MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);
        if (isFirstTime) {
            generateRandomName();
            edit.putBoolean("isFirstTime", false).apply();
            edit.putString("playerName",GameState.getPlayerName());
            edit.putInt("playerLevel",0);
            edit.putFloat("playerIQ", 0);
            edit.putBoolean("isFirstTime",false);
            edit.putBoolean("isActivePlayer",true);
            edit.apply();
        } else {
            // Load game state
            GameState.setPlayerName(preferences.getString("playerName", "Default Player"));
            GameState.setPlayerLevel(preferences.getInt("playerLevel", 0));
            GameState.setPlayerIQ(preferences.getFloat("playerIQ", 0.0F));
            GameState.setFirstTime(false);
        }
        handler= new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, ActivityMenu.class);
                startActivity(intent);

            }
        },3000);


    }




    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish(); // Close the game
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

    // Method to save game state to SharedPreferences

    // Generate random name
    private void generateRandomName() {
        Random random = new Random();
        String randomName = "Guest" + random.nextInt(100000);
        // Save the generated name
        GameState.setPlayerName(randomName);

    }
}