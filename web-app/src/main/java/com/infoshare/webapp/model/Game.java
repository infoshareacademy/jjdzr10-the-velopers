package com.infoshare.webapp.model;

import com.infoshare.webapp.Dto.AnswerDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Game {
    private User user;
    private List<Question> questions;
    private Category category;
    private List<AnswerDto> allUserAnswers;
    private Integer amountQuestions;
    private boolean mixQuestions;
    private boolean mixAnswers;
    private boolean isTimer;
    private Integer level;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<AnswerDto> getAllUserAnswers() {
        return allUserAnswers;
    }

    public void setAllUserAnswers(List<AnswerDto> allUserAnswers) {
        this.allUserAnswers = allUserAnswers;
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
                "user=" + user +
                ", questions=" + questions +
                ", category=" + category +
                ", allUserAnswers=" + allUserAnswers +
                ", amountQuestions=" + amountQuestions +
                ", mixQuestions=" + mixQuestions +
                ", mixAnswers=" + mixAnswers +
                ", level=" + level +
                '}';
    }
}
