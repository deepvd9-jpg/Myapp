package com.tournament.app.utils;

public class XPLevelManager {

    public static int getLevel(int totalXP) {
        // Example logic: Level increases every 1000 XP
        return totalXP / 1000 + 1;
    }

    public static int getXPForNextLevel(int currentLevel) {
        // Example logic: Next level requires 1000 XP more than current level
        return currentLevel * 1000;
    }

    public static int getProgressToNextLevel(int totalXP) {
        int currentLevel = getLevel(totalXP);
        int xpForCurrentLevel = (currentLevel - 1) * 1000;
        return totalXP - xpForCurrentLevel;
    }
}
