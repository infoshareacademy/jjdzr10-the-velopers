package com.infoshare.webapp.service;

import com.infoshare.webapp.exception.QuestionsNotFoundException;
import com.infoshare.webapp.model.Question;
import com.infoshare.webapp.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class QuestionService {

    private static List<Question> questions;
    private final ReadFileService readFileService;

    private final QuestionRepository questionRepository;

    public QuestionService(ReadFileService readFileService, QuestionRepository questionRepository) throws IOException {
        this.readFileService = readFileService;
        this.questionRepository = questionRepository;
        List<Question> questionsFromFile = readFileService.loadQuestions();
        questionRepository.saveAll(questionsFromFile);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public long getLastQuestionId() {
        long lastQuestionId = 0L;
        if (!questions.isEmpty()) {
            lastQuestionId = questions.stream().max(Comparator.comparing(Question::getIdQuestion)).get().getIdQuestion();
        }
        return lastQuestionId;
    }

    public void editQuestion(long id, Question question) {
        Question questionToEdit = findById(id);
        questionToEdit.setAnswers(question.getAnswers());
        questionToEdit.setCategory(question.getCategory());
        questionToEdit.setQuestionText(question.getQuestionText());
        questionToEdit.setScore(question.getScore());
        questionRepository.save(questionToEdit);
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

    public Question findById(long id) {
        return questions.stream()
                .filter(question -> question.getIdQuestion() == id)
                .findFirst()
                .orElseThrow(() -> new QuestionsNotFoundException(String.format("Not found question with given Id:  %S", id)));
    }

    public void removeQuestionById(long id) {
        questionRepository.deleteById(id);
    }

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }
}














