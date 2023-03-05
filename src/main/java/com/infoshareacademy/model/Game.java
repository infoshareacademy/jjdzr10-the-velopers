package com.infoshareacademy.model;

import java.util.List;

public class Game {
    private User user;
    private List<Questions> questions;
    private boolean AllUserAnswers[][];

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean[][] getAllUserAnswers() {
        return AllUserAnswers;
    }

    public void setAllUserAnswers(boolean[][] allUserAnswers) {
        AllUserAnswers = allUserAnswers;
    }
}
