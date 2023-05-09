package com.infoshare.webapp.model;

import java.util.List;

public class Game {
    private User user;
    private List<Question> questions;
    private boolean allUserAnswers[][];
    private Integer amountQuestions;
    private boolean mixQuestions;
    private boolean mixAnswers;
    private Integer level;

    public Integer getAmountQuestions() {
        return amountQuestions;
    }

    public void setAmountQuestions(Integer amountQuestions) {
        this.amountQuestions = amountQuestions;
    }

    public boolean isMixQuestions() {
        return mixQuestions;
    }

    public void setMixQuestions(boolean mixQuestions) {
        this.mixQuestions = mixQuestions;
    }

    public boolean isMixAnswers() {
        return mixAnswers;
    }

    public void setMixAnswers(boolean mixAnswers) {
        this.mixAnswers = mixAnswers;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean[][] getAllUserAnswers() {
        return allUserAnswers;
    }

    public void setAllUserAnswers(boolean[][] allUserAnswers) {
        this.allUserAnswers = allUserAnswers;
    }
}
