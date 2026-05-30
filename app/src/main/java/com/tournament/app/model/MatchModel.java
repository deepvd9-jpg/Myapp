package com.tournament.app.model;

import java.util.List;

public class MatchModel {
    private String matchId;
    private String mapName;
    private long startTime;
    private String matchState; // upcoming, playing, ended
    private String roomId;
    private String password;
    private String mode; // Solo, Duo, Squad
    private List<String> gameUIDs; // For joined users
    private String resultStatus; // Pending, Won, Lost
    private int prizeDistributed;
    private int xpDistributed;

    public MatchModel() {
        // Default constructor required for Firestore
    }

    public MatchModel(String matchId, String mapName, long startTime, String matchState, String roomId, String password, String mode, List<String> gameUIDs, String resultStatus, int prizeDistributed, int xpDistributed) {
        this.matchId = matchId;
        this.mapName = mapName;
        this.startTime = startTime;
        this.matchState = matchState;
        this.roomId = roomId;
        this.password = password;
        this.mode = mode;
        this.gameUIDs = gameUIDs;
        this.resultStatus = resultStatus;
        this.prizeDistributed = prizeDistributed;
        this.xpDistributed = xpDistributed;
    }

    // Getters and Setters
    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getMatchState() {
        return matchState;
    }

    public void setMatchState(String matchState) {
        this.matchState = matchState;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<String> getGameUIDs() {
        return gameUIDs;
    }

    public void setGameUIDs(List<String> gameUIDs) {
        this.gameUIDs = gameUIDs;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public int getPrizeDistributed() {
        return prizeDistributed;
    }

    public void setPrizeDistributed(int prizeDistributed) {
        this.prizeDistributed = prizeDistributed;
    }

    public int getXpDistributed() {
        return xpDistributed;
    }

    public void setXpDistributed(int xpDistributed) {
        this.xpDistributed = xpDistributed;
    }
}
