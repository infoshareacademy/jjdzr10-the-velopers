package com.infoshare.webapp.service;

import com.infoshare.webapp.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class GameService {

    private final Game game;

    public GameService(Game game){
        this.game = game;
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
    private List<Questions> setQuizCategory() {
        return game.getQuestions().stream().filter(question -> question.getCategory().equals(game.getCategory())).toList();
    }
    private List<Questions> limitQuestions() {
        return game.getQuestions().stream().limit(game.getAmountQuestions()).toList();
    }

    private List<Questions> shufflingQuestions() {
        List<Questions> shuffleQuestions = new ArrayList<>(game.getQuestions());
        Collections.shuffle(shuffleQuestions);
        return shuffleQuestions;
    }

    private void shuffleAnswers() {
        for (Questions question : game.getQuestions()) {
            shuffleTwoList(question.getAnswer().getAnswers(), question.getAnswer().getCorrectAnswers());
        }
    }
    private static <T> void shuffleTwoList(List<String> a, List<T> b) {
        Random random = new Random();
        int n = a.size();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
            swap(b, i, change);
        }
    }

    private static <T> void swap(List<T> a, int i, int change) {
        T helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
    }
    private void clearUserAnswers() {
        game.setAllUserAnswers(new ArrayList<>());
        for (Questions question : game.getQuestions()) {
            Answers clearAnswer = new Answers();
            clearAnswer.setCorrectAnswers(Collections.nCopies(question.getAnswer().getCorrectAnswers().size(), false));
            game.getAllUserAnswers().add(clearAnswer);
        }
    }
}