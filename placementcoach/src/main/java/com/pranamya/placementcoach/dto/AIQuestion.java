package com.pranamya.placementcoach.dto;

public class AIQuestion {

    private String skill;
    private String question;
    private String difficulty;

    public AIQuestion() {
    }

    public AIQuestion(String skill, String question, String difficulty) {
        this.skill = skill;
        this.question = question;
        this.difficulty = difficulty;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}