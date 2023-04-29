package com.infoshare.webapp.service;

import com.infoshare.webapp.exception.QuestionsNotFoundException;
import com.infoshare.webapp.model.Questions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Service
public class QuestionService {

    private static List<Long> availableQuestionNumbers;
    private static Scanner scanner;
    private static List<Questions> questionsList;
    private final ReadFileService readFileService;

    public QuestionService(ReadFileService readFileService) throws IOException {
        this.readFileService = readFileService;
        questionsList = this.readFileService.loadQuestions();
    }

    public List<Questions> getAllQuestions() {
        return questionsList;
    }

    public void editQuestion(Long id, Questions question) {
        Questions questionToEdit = findById(id);
        questionToEdit.setAnswer(question.getAnswer());
        questionToEdit.setCategory(question.getCategory());
        questionToEdit.setQuestionText(question.getQuestionText());
        questionToEdit.setScore(question.getScore());
    }

    public Questions findById(Long id) {
        return questionsList.stream()
                .filter(question -> question.getIdQuestion() == id)
                .findFirst()
                .orElseThrow(() -> new QuestionsNotFoundException(String.format("Not found question with given Id:  %S", id)));
    }

    public void removeQuestionById(Long id) {
        Questions foundQuestion = findById(id);
        questionsList.remove(foundQuestion);
    }

    public void addQuestion(Questions question) {
        questionsList.add(question);
    }

}














