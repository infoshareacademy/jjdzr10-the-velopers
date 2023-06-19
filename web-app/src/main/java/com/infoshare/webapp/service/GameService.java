package com.infoshare.webapp.service;

import com.infoshare.webapp.Dto.AnswerDto;
import com.infoshare.webapp.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final UserGame game;
    private final User user;


    public GameService(UserGame game, User user) {
        this.game = game;
        this.user = user;
    }
    public List<Question> getAllQuestions() {
        return game.getGameQuestions();
    }
    public long getFirstQuestionId() {
        long firstQuestionId = 0L;
        if (!game.getGameQuestions().isEmpty()) {
            firstQuestionId = game.getGameQuestions().stream().min(Comparator.comparing(Question::getId)).get().getId();
        }
        return firstQuestionId;
    }
    public Question getQuestionByIndex(int index) throws Exception {
        try {
            return game.getGameQuestions().get(index);
        }
        catch (Exception e){
            throw new Exception("Question index out of bound: {value: "+index+" }");
        }
    }
    public AnswerDto getUserAnswer(Question question) {
        int questionIndex = game.getGameQuestions().indexOf(question);
        return game.getUserAnswers().get(questionIndex);
    }
    public boolean isTimer() {
        return game.isTimer();
    }
    public void setUserAnswer(Question question, AnswerDto userAnswer) {
        while (question.getAnswers().size() != userAnswer.getAnswers().size()) {
            userAnswer.addAnswer(new Answer());
        }
        int questionIndex = game.getGameQuestions().indexOf(question);
        game.getUserAnswers().set(questionIndex, userAnswer);

    }
    public void settingsGame(UserGame newGame) {
        game.setUser(user);
        game.setUserAnswers(new ArrayList<>());
        game.setGameQuestions(newGame.getGameQuestions());
        game.setAmountQuestions(newGame.getAmountQuestions());
        game.setCategory(newGame.getCategory());
        game.setMixQuestions(newGame.isMixQuestions());
        game.setMixAnswers(newGame.isMixAnswers());
        if (game.getCategory() != null) {
            game.setGameQuestions(setQuizCategory());
        }
        if (game.isMixQuestions()) {
            game.setGameQuestions(shufflingQuestions());
        }
        if (game.getAmountQuestions() != null) {
            game.setGameQuestions(limitQuestions());
        }
        if (game.isMixAnswers()) {
            shuffleAnswers();
        }
       clearUserAnswers();
    }
    private List<Question> setQuizCategory() {
        return game.getGameQuestions().stream().filter(question -> question.getCategory().equals(game.getCategory())).toList();
    }
    private List<Question> limitQuestions() {
        return game.getGameQuestions().stream().limit(game.getAmountQuestions()).toList();
    }

    private List<Question> shufflingQuestions() {
        List<Question> shuffleQuestions = new ArrayList<>(game.getGameQuestions());
        Collections.shuffle(shuffleQuestions);
        return shuffleQuestions;
    }

    private void shuffleAnswers() {
        for (Question question : game.getGameQuestions()) {
            List<Answer> shuffleAnswers = new ArrayList<>(question.getAnswers());
            Collections.shuffle(shuffleAnswers);
            question.setAnswers(shuffleAnswers);
        }
    }

    private void clearUserAnswers() {
        Answer clearAnswer = new Answer();
        clearAnswer.setCorrect(false);
        game.setUserAnswers(new ArrayList<>());

        for (Question question : game.getGameQuestions()) {
            AnswerDto answerDto = new AnswerDto();
            while (answerDto.getAnswers().size() != question.getAnswers().size()) {
                answerDto.addAnswer(clearAnswer);
            }
            game.getUserAnswers().add(answerDto);
        }
    }

    public List<AnswerDto> getAllUserAnswer() {
        return game.getUserAnswers();
    }

    public List<Boolean> userAnsweredList() {
        List<Boolean> booleans = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < game.getUserAnswers().size(); i++) {
            for (int j = 0; j < game.getUserAnswers().get(i).getAnswers().size(); j++) {
                flag = game.getUserAnswers().get(i).getAnswers().get(j).getCorrect();
                if (flag) { break;}
            }
            booleans.add(flag);
        }
        return booleans;
    }
    public List<Boolean> userAnsweredCorrectList() {
        List<Boolean> booleans = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < game.getGameQuestions().size(); i++) {
            for (int j = 0; j < game.getGameQuestions().get(i).getAnswers().size(); j++) {
                flag = game.getGameQuestions().get(i).getAnswers().get(j).getCorrect() == game.getUserAnswers().get(i).getAnswers().get(j).getCorrect();
                if (!flag) { break;}
            }
            booleans.add(flag);
        }
        return booleans;
    }

    public int calculateScore() {
        int score = 0;
        List<Boolean> booleans = userAnsweredCorrectList();
        for (int i=0 ; i<game.getGameQuestions().size(); i++){
            if (booleans.get(i)) {
                score += game.getGameQuestions().get(i).getScore();
            }
        }
        game.setUserScore(score);
        return score;
    }

//    public Boolean compareAnswers(Answer answer, AnswerDto userAnswer) {
//        return answer.getCorrect() == (userAnswer.getCorrect());
//    }

}