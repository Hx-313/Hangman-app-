package com.example.hangmangame;

public class GameState {
    // Static variables
    private static String playerName = "Default Player";
    private static int playerLevel = 0;
    private static float playerIQ = 0.0F;
    private static boolean isFirstTime = true;
    private static boolean vibrationState = true;
    private static String playerId;

    // Private constructor to prevent instantiation
    private GameState() {
        // This constructor is intentionally empty to prevent instantiation.
    }

    // Getter and Setter methods for playerName
    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(String playerName) {
        GameState.playerName = playerName;
    }

    // Getter and Setter methods for playerLevel
    public static int getPlayerLevel() {
        return playerLevel;
    }

    public static void setPlayerLevel(int playerLevel) {
        GameState.playerLevel = playerLevel;
    }

    // Getter and Setter methods for isFirstTime
    public static boolean isFirstTime() {
        return isFirstTime;
    }

    public static void setFirstTime(boolean isFirstTime) {
        GameState.isFirstTime = isFirstTime;
    }

    public static float getPlayerIQ() {
        return playerIQ;
    }
    public static void setPlayerIQ(float playerIQ) {
        GameState.playerIQ = playerIQ;
    }


    public static boolean isVibrationState() {
        return vibrationState;
    }

    public static void setVibrationState(boolean vibrationState) {
        GameState.vibrationState = vibrationState;
    }

    public static String getPlayerId() {
        return playerId;
    }

    public static void setPlayerId(String playerId) {
        GameState.playerId = playerId;
    }
}

