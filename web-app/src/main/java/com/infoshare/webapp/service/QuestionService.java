package com.infoshare.webapp.service;

import com.infoshare.webapp.exception.QuestionsNotFoundException;
import com.infoshare.webapp.model.Questions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


@Service
public class QuestionService {

    private static List<Questions> questionsList;
    private final ReadFileService readFileService;

    public QuestionService(ReadFileService readFileService) throws IOException {
        this.readFileService = readFileService;
        questionsList = this.readFileService.loadQuestions();
    }

    public List<Questions> getAllQuestions() {
        return questionsList;
    }

    public Long getLastQuestionId() {
        Long lastQuestionId = 0L;
        if (!questionsList.isEmpty()) {
            lastQuestionId = questionsList.stream().max(Comparator.comparing(Questions::getIdQuestion)).get().getIdQuestion();
        }
        return lastQuestionId;
    }

    public void editQuestion(Long id, Questions question) {
        Questions questionToEdit = findById(id);
        questionToEdit.getAnswer().setCorrectAnswers(editAnswer(questionToEdit.getAnswer().getCorrectAnswers(), question.getAnswer().getCorrectAnswers()));
        questionToEdit.setCategory(question.getCategory());
        questionToEdit.setQuestionText(question.getQuestionText());
        questionToEdit.setScore(question.getScore());
    }
    public List<Boolean> editAnswer(List<Boolean> answerToEdit, List<Boolean> userAnswer){
        ArrayList<Boolean> booleans = new ArrayList<>(Collections.nCopies(answerToEdit.size(), false));
        for (int i=0; i<userAnswer.size(); i++) {
            if (userAnswer.get(i) != null) {
                booleans.set(i, userAnswer.get(i));
            }
        }
        return booleans;
    }

    public Questions findById(long id) {
        return questionsList.stream()
                .filter(question -> question.getIdQuestion() == id)
                .findFirst()
                .orElseThrow(() -> new QuestionsNotFoundException(String.format("Not found question with given Id:  %S", id)));
    }

    public void removeQuestionById(long id) {
        Questions foundQuestion = findById(id);
        questionsList.remove(foundQuestion);
    }

    public void addQuestion(Questions question) {
        questionsList.add(question);
    }

}














