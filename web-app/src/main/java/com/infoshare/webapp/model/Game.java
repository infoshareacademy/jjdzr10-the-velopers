package com.infoshare.webapp.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Game {
    private Category category;
    private Integer amountQuestions;
    private boolean mixQuestions;
    private boolean mixAnswers;
    private boolean isTimer;
    private Integer level;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
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

    public boolean isTimer() {
        return isTimer;
    }

    public void setTimer(boolean timer) {
        isTimer = timer;
    }

    @Override
    public String toString() {
        return "Game{" +
                "category=" + category +
                ", amountQuestions=" + amountQuestions +
                ", mixQuestions=" + mixQuestions +
                ", mixAnswers=" + mixAnswers +
                ", level=" + level +
                '}';
    }
}
