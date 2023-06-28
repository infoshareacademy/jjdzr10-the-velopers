package com.infoshare.webapp.service;

import com.infoshare.webapp.model.Question;
import com.infoshare.webapp.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class QuestionService {

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

    public void editQuestion(long id, Question question) {
        question.setId(id);
        questionRepository.save(question);
    }

    public List<Boolean> editAnswer(List<Boolean> answerToEdit, List<Boolean> userAnswer) {
        ArrayList<Boolean> booleans = new ArrayList<>(Collections.nCopies(answerToEdit.size(), false));
        for (int i = 0; i < userAnswer.size(); i++) {
            if (userAnswer.get(i) != null) {
                booleans.set(i, userAnswer.get(i));
            }
        }
        return booleans;
    }

    public Question findById(long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public void removeQuestionById(long id) {
        questionRepository.deleteById(id);
    }

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    public Page<Question> getPagedQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }
}














