package com.pranamya.placementcoach.dto;

public class ReadinessResponse {

    private int score;
    private String level;
    private String message;

    public ReadinessResponse(int score, String level, String message) {
        this.score = score;
        this.level = level;
        this.message = message;
    }

    public int getScore() {
        return score;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
}