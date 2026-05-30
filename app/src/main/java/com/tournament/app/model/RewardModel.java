package com.tournament.app.model;

public class RewardModel {
    private String rewardId;
    private String name;
    private String description;
    private int xpReward;
    private double cashReward;
    private long cooldownMillis;
    private boolean claimed;

    public RewardModel() {
        // Default constructor required for Firestore
    }

    public RewardModel(String rewardId, String name, String description, int xpReward, double cashReward, long cooldownMillis, boolean claimed) {
        this.rewardId = rewardId;
        this.name = name;
        this.description = description;
        this.xpReward = xpReward;
        this.cashReward = cashReward;
        this.cooldownMillis = cooldownMillis;
        this.claimed = claimed;
    }

    // Getters and Setters
    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public double getCashReward() {
        return cashReward;
    }

    public void setCashReward(double cashReward) {
        this.cashReward = cashReward;
    }

    public long getCooldownMillis() {
        return cooldownMillis;
    }

    public void setCooldownMillis(long cooldownMillis) {
        this.cooldownMillis = cooldownMillis;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }
}
