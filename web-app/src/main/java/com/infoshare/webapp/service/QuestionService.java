package com.infoshare.webapp.service;

import com.infoshare.webapp.Dao.QuestionDao;
import com.infoshare.webapp.exception.QuestionsNotFoundException;
import com.infoshare.webapp.model.Question;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class QuestionService {

    private static List<Question> questions;
    private final ReadFileService readFileService;
    private final QuestionDao questionDao;

    public QuestionService(ReadFileService readFileService,QuestionDao questionDao) throws IOException {
        this.readFileService = readFileService;
        List<Question> questionsFromFile = readFileService.loadQuestions();

        this.questionDao = questionDao;
        questionsFromFile.forEach(questionDao::save);

        questions = questionDao.findAll();
    }



    public List<Question> getAllQuestions() {
        return questions;
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
        questionDao.update(questionToEdit);
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
        Question foundQuestion = findById(id);
        questionDao.deleteById(id);
        questions.remove(foundQuestion);
    }

    public void addQuestion(Question question) {
        questionDao.save(question);
        questions.add(question);
    }
}














