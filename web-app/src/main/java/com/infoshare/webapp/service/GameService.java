package com.infoshare.webapp.service;

import com.infoshare.webapp.Dto.AnswerDto;
import com.infoshare.webapp.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final Game game;
    private final QuestionService questionService;

    public GameService(Game game, QuestionService questionService){
        this.game = game;
        this.questionService = questionService;
    }
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }
    public long getFirstQuestionId() {
        long firstQuestionId = 0L;
        if (!game.getQuestions().isEmpty()) {
            firstQuestionId = game.getQuestions().stream().min(Comparator.comparing(Question::getIdQuestion)).get().getIdQuestion();
        }
        return firstQuestionId;
    }
    public AnswerDto getUserAnswer(Question question) {
        int questionIndex = game.getQuestions().indexOf(question);
        return game.getAllUserAnswers().get(questionIndex);
    }
    public boolean isTimer(){
        return game.isTimer();
    }
    public void setUserAnswer(Question question, AnswerDto userAnswer) {
            while (question.getAnswers().size() != userAnswer.getAnswers().size()){
                userAnswer.addAnswer(new Answer());
            }
        int questionIndex = game.getQuestions().indexOf(question);
        game.getAllUserAnswers().set(questionIndex, userAnswer);
    }
    public Boolean compareAnswers(Answer answer, Answer userAnswer) {
        return answer.getCorrect()==(userAnswer.getCorrect());
    }

    public void settingsGame(Game newGame) {
        game.setUser(newGame.getUser());
        game.setAllUserAnswers(new ArrayList<>());
        game.setQuestions(newGame.getQuestions());
        game.setAmountQuestions(newGame.getAmountQuestions());
        game.setCategory(newGame.getCategory());
        game.setMixQuestions(newGame.isMixQuestions());
        game.setMixAnswers(newGame.isMixAnswers());
        if (game.getCategory() != null) {
            game.setQuestions(setQuizCategory());
        }
        if (game.isMixQuestions()) {
            game.setQuestions(shufflingQuestions());
        }
        if (game.getAmountQuestions() != null){
            game.setQuestions(limitQuestions());
        }
        if (game.isMixAnswers()) {
            shuffleAnswers();
        }
        clearUserAnswers();
    }
    private List<Question> setQuizCategory() {
        return game.getQuestions().stream().filter(question -> question.getCategory().equals(game.getCategory())).toList();
    }
    private List<Question> limitQuestions() {
        return game.getQuestions().stream().limit(game.getAmountQuestions()).toList();
    }

    private List<Question> shufflingQuestions() {
        List<Question> shuffleQuestions = new ArrayList<>(game.getQuestions());
        Collections.shuffle(shuffleQuestions);
        return shuffleQuestions;
    }

    private void shuffleAnswers() {
        for (Question question : game.getQuestions()) {
            List<Answer> shuffleAnswers = new ArrayList<>(question.getAnswers());
            Collections.shuffle(shuffleAnswers);
            question.setAnswers(shuffleAnswers);
        }
    }

    private void clearUserAnswers() {
        Answer clearAnswer = new Answer();
        clearAnswer.setCorrect(false);
        game.setAllUserAnswers(new ArrayList<>());

        for (Question question : game.getQuestions()){
            AnswerDto answerDto = new AnswerDto();
            for (int i=0;i<question.getAnswers().size();i++){
                answerDto.addAnswer(clearAnswer);
            }
            game.getAllUserAnswers().add(answerDto);
        }
    }
}