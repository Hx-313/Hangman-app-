package com.example.hangmangame;


import android.content.Context;
import android.content.Intent;

public class MusicUtil {
    public static void startMusicService(Context context, int trackResource) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra("track", trackResource);
        context.startService(intent);
    }

    public static void stopMusicService(Context context) {
        Intent intent = new Intent(context, MusicService.class);
        context.stopService(intent);
    }

    public static void changeMusic(Context context, int trackResource) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra("track", trackResource);
        context.startService(intent);
    }
}
