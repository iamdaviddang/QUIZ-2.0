package com.daviddang.quiz;

public class Question {
    private int id;
    private String question;
    private String correctAnswer;
    private String[] options;

    public Question(int id, String question, String correctAnswer, String[] options) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getOptions() {
        return options;
    }
}
