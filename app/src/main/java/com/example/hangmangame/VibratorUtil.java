package com.example.hangmangame;



import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibratorUtil {
    public static void vibrate(Context context, long duration) {
        if (GameState.isVibrationState()) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(duration);
                }
            }
        }
    }
}
