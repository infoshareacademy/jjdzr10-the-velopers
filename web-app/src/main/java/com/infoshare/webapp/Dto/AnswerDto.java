package com.infoshare.webapp.Dto;

import com.infoshare.webapp.model.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerDto {
    private List<Answer> answers = new ArrayList<>();


    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
