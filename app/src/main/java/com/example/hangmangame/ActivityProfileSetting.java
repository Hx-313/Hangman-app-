package com.example.hangmangame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.logging.Level;

public class ActivityProfileSetting extends AppCompatActivity {
EditText name;
ImageButton back;
ImageView editname;
Button confirm;
TextView level , iq , result;
ImageButton swithAcoount;
Button tryAgain;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        View alertCustomDiaologue = LayoutInflater.from(ActivityProfileSetting.this).inflate(R.layout.state_dialogue,null);
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(ActivityProfileSetting.this);
        alertDialogue.setView(alertCustomDiaologue);
        final AlertDialog dialog = alertDialogue.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SharedPreferences preferences1 = getSharedPreferences("User1", MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("User2", MODE_PRIVATE);



        SharedPreferences.Editor edit1 = preferences1.edit();
        SharedPreferences.Editor edit2 = preferences2.edit();

        result =alertCustomDiaologue.findViewById(R.id.result);
        tryAgain = alertCustomDiaologue.findViewById(R.id.tryAgain);
        tryAgain.setText("Create...");
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit2.putBoolean("isActivePlayer",true);
                edit1.putBoolean("isActivePlayer",false);
                Intent intent = new Intent(ActivityProfileSetting.this, MainActivity.class); // Replace with your actual first activity class
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishAffinity();
            }
        });

        name = findViewById(R.id.playername);
        name.setEnabled(false);
        name.setHint(GameState.getPlayerName());
        back= findViewById(R.id.back);
        confirm = findViewById(R.id.confirm);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        editname = findViewById(R.id.editName);

        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityProfileSetting.this, 100); // Vibrate for 100 milliseconds


                name.setEnabled(true);
                    name.setHint("");
                    confirm.setVisibility(View.VISIBLE);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibratorUtil.vibrate(ActivityProfileSetting.this, 100); // Vibrate for 100 milliseconds

                name.setEnabled(false);
                GameState.setPlayerName(String.valueOf(name.getText()));
                SharedPreferences pref = getSharedPreferences(GameState.getPlayerId(), MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("playerName", GameState.getPlayerName());
                editor.apply();
                name.setHint(GameState.getPlayerName());
                name.setText("");
                confirm.setVisibility(View.INVISIBLE);
            }
        });
        swithAcoount = findViewById(R.id.Switch);
        swithAcoount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    boolean isActive1 = preferences1.getBoolean("isActivePlayer", true);

                    // Switch active player based on current state
                String Name;
                int level;
                float iq;
                    if (isActive1) {
                        GameState.setPlayerId("User2");
                        edit1.putBoolean("isActivePlayer", false);
                        edit2.putBoolean("isActivePlayer", true);
                         Name = preferences2.getString("playerName", "Default Player"); // Assuming Player 1's name
                         level = preferences2.getInt("playerLevel", 0);
                         iq = preferences2.getFloat("playerIQ", 0);
                    } else {
                        GameState.setPlayerId("User1");
                        edit1.putBoolean("isActivePlayer", true);
                        edit2.putBoolean("isActivePlayer", false);
                         Name = preferences1.getString("playerName", "Default Player"); // Assuming Player 1's name
                         level = preferences1.getInt("playerLevel", 0);
                         iq = preferences1.getFloat("playerIQ", 0);
                    }

                    // Apply changes to both SharedPreferences files
                    edit1.apply();
                    edit2.apply();

                    // Load data for the active player
                    boolean isFirstTime = preferences2.getBoolean("isFirstTime", true); // Assuming this is for Player 2


                    // Handle new player creation logic (assuming it's for Player 2)
                    if (isFirstTime) {
                        result.setText("Are you sure to Create new Player?");
                        dialog.show();
                        // Update "isFirstTime" to false after creation
                    } else {
                        showDialog(Name, level, iq);
                    }



            }
        });


        level = findViewById(R.id.level);
        level.setText(level.getText() + String.valueOf(GameState.getPlayerLevel()));

        iq = findViewById(R.id.iq);
        iq.setText(iq.getText() + String.valueOf(GameState.getPlayerIQ()));

        Switch vibrationSwitch = findViewById(R.id.vibration);

        // Set the switch state based on GameState
        vibrationSwitch.setChecked(GameState.isVibrationState());

        // Listen for switch state changes
        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameState.setVibrationState(isChecked);
            }
        });
;
    }

    void showDialog(String userName, int level, float iq){
        View alertCustomDiaologue =
                LayoutInflater.from(ActivityProfileSetting.this).
                        inflate(R.layout.activity_switch_account,null);
        AlertDialog.Builder alertDialogue =
                new AlertDialog.Builder(ActivityProfileSetting.this);
        alertDialogue.setView(alertCustomDiaologue);
        final AlertDialog dialog = alertDialogue.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView wordArea = alertCustomDiaologue.findViewById(R.id.wordArea);
        TextView triesArea = alertCustomDiaologue.findViewById(R.id.usedTries);
        TextView modeArea = alertCustomDiaologue.findViewById(R.id.modeArea);
        TextView Greeting = alertCustomDiaologue.findViewById(R.id.greeting);
        TextView Greeting2 = alertCustomDiaologue.findViewById(R.id.greeting2);
        wordArea.setText(userName);
        modeArea.setText(String.valueOf(iq));
        Greeting.setText("Switching Accounts");
        Greeting2.setText("Are you sure you want to switch Account?");
        triesArea.setText(String.valueOf(level));
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Button continu = alertCustomDiaologue.findViewById(R.id.btnfollow);
        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent = new Intent(ActivityProfileSetting.this, MainActivity.class); // Replace with your actual first activity class
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishAffinity();

            }
        });
        Button again = alertCustomDiaologue.findViewById(R.id.btnTryAGain);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
    }
}