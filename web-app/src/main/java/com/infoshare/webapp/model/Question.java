package com.infoshare.webapp.model;

import java.util.List;

public class Question {
    private long idQuestion;
    private Category category;
    private List<Answer> answers;
    private String questionText;
    private int score;

    public long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Question{" +
                "idQuestion=" + idQuestion +
                ", category=" + category +
                ", answers=" + answers +
                ", questionText='" + questionText + '\'' +
                ", score=" + score +
                '}';
    }
}

