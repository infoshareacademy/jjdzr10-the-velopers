package com.infoshareacademy.model;

public class Answers {
    private String[] answers = new String[4];
    private boolean[] correctAnswers = new boolean[4];

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public boolean[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(boolean[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
