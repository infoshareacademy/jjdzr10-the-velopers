package com.infoshare.webapp.model;

import javax.validation.constraints.NotEmpty;

public class Questions {
    private Long idQuestion;
    private Category category;
    private Answers answer;
    @NotEmpty(message = "The question text cannot be empty")
    private String questionText;
    private int score;

    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Answers getAnswer() {
        return answer;
    }

    public void setAnswer(Answers answer) {
        this.answer = answer;
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
        return "Questions{" +
                "idQuestion=" + idQuestion +
                ", category='" + category + '\'' +
                ", answer=" + answer +
                ", questionText='" + questionText + '\'' +
                ", score=" + score +
                '}';
    }
}

