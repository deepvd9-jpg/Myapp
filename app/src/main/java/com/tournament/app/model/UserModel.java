package com.tournament.app.model;

public class UserModel {
    private String uid;
    private String username;
    private int totalXP;
    private int matchesPlayed;
    private int totalKills;
    private boolean vipStatus;
    private String profileAvatarUrl;

    public UserModel() {
        // Default constructor required for Firestore
    }

    public UserModel(String uid, String username, int totalXP, int matchesPlayed, int totalKills, boolean vipStatus, String profileAvatarUrl) {
        this.uid = uid;
        this.username = username;
        this.totalXP = totalXP;
        this.matchesPlayed = matchesPlayed;
        this.totalKills = totalKills;
        this.vipStatus = vipStatus;
        this.profileAvatarUrl = profileAvatarUrl;
    }

    // Getters and Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public boolean isVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(boolean vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getProfileAvatarUrl() {
        return profileAvatarUrl;
    }

    public void setProfileAvatarUrl(String profileAvatarUrl) {
        this.profileAvatarUrl = profileAvatarUrl;
    }
}
