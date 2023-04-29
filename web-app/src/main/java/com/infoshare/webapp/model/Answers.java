package com.infoshare.webapp.model;

import java.util.List;

public class Answers {
    private List<String> answers;
    private List<Boolean> correctAnswers;

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<Boolean> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<Boolean> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}