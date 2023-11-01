package com.daviddang.quiz;

public class Team {
    String name;
    int score;

    public Team(String name, int score) {
        this.name = name;
        this.score = 0;
    }

    public void addScore(int points) {
        score += points;
    }

    public void subtractScore(int points) {
        score -= points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

