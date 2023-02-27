package com.infoshareacademy.service;

import com.infoshareacademy.model.Game;
import com.infoshareacademy.model.Questions;
import com.infoshareacademy.model.User;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class GameService {
    private static Game game = new Game();

    public static void start(List<Questions> questions, User user) throws InterruptedException {
        game.setUser(user);
        game.setQuestions(questions);

        System.out.println("START NEW GAME: ");
        displayQuestions(questions);
        finalResult(questions);
    }

    private static void displayQuestions(List<Questions> questionsList) throws InterruptedException {
        int questionNumber = 0;
        game.setAllUserAnswers(new boolean[questionsList.size()][4]);
        for (Questions quest : questionsList) {
            questionNumber++;
            // display the questions
            System.out.println();
            System.out.print(questionNumber + ". Pytanie: ");
            System.out.println(quest.getQuestionText());
            // view available answers
            int intLetter = (65);
            for (String ans : quest.getAnswer().getAnswers()) {
                System.out.print(((char) intLetter) + " : ");
                System.out.println(ans);
                intLetter++;
            }
            // retrieving responses from the player
            boolean[] userAnswers = QuestionService.getCorrectAnswer();
            game.getAllUserAnswers()[(questionNumber - 1)] = userAnswers;
            // checking the answer
            boolean answeredCorrect = checkAnswer(quest.getAnswer().getCorrectAnswers(), userAnswers);
            // assigning points for correct answers
            if (answeredCorrect) {
                game.getUser().getUserScore().setHighestScore(game.getUser().getUserScore().getHighestScore() + quest.getScore());
            }
            // next question delay [1 second]
            TimeUnit.SECONDS.sleep(1);
        }

    }

    private static boolean checkAnswer(boolean[] questionAnswers, boolean[] userAnswers) {
        boolean answeredCorrect = false;
        for (int i = 0; i < questionAnswers.length; i++) {
            answeredCorrect = questionAnswers[i] == userAnswers[i];
            if (!answeredCorrect) {
                break;
            }
        }
        if (answeredCorrect) {
            System.out.println("Twoja odpowiedź jest poprawna!!!");
        } else {
            System.out.println("Zła odpowiedź :(");
        }
        return answeredCorrect;
    }

    private static void finalResult(List<Questions> questions) {
        System.out.println("\t+++\t");
        System.out.println("Użytkowniku " + game.getUser().getUserName());
        System.out.println("gratulujemy wyniku: " + game.getUser().getUserScore().getHighestScore() + " pkt");
        System.out.println("Oto tabela wyników:");
        System.out.println("Odpowiedzi użytkownika podane są w < > nawiasach,\n" +
                "poprawne odpowiedzi podane są w ( ) nawiasach.");

        int i = 0;
        for (Questions question : questions) {
            System.out.println(question.getQuestionText());
            for (int j = 0; j < 4; j++) {
                String out = question.getAnswer().getAnswers()[j];
                if (question.getAnswer().getCorrectAnswers()[j]) {
                    out = "(" + out + ")";
                }
                if (game.getAllUserAnswers()[i][j]) {
                    out = "<" + out + ">";
                }
                out = out + " ";

                System.out.print(out);
            }
            System.out.println("\n\t---\t");
            i++;
        }
    }
}