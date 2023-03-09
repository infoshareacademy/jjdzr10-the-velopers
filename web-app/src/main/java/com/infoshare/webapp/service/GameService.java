package com.infoshare.webapp.service;

import com.infoshare.webapp.model.Game;
import com.infoshare.webapp.model.Questions;
import com.infoshare.webapp.model.Score;
import com.infoshare.webapp.model.User;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class GameService {

    private static Game game = new Game();
    private static Scanner scanner;
    static final String questSeparator = "%n--- Pytanie %s ---%n %s %n";

    public GameService(List<Questions> questions, User user) throws InterruptedException {
        user.setUserScore(new Score());
        start(questions, user);
    }

    private static void start(List<Questions> questionsList, User user) throws InterruptedException {
        game.setUser(user);
        game.setQuestions(questionsList);
        // make clean array AllUserAnswers
        game.setAllUserAnswers(new boolean[questionsList.size()][4]);
        // make settings game
        settingsGame();

        System.out.println("START NEW GAME: ");
        displayQuestions(questionsList);
        finalResult(questionsList);
    }

    private static void settingsGame() {
        while (true) {
            try {
                System.out.print("Ile pyań wyświetlić: ");
                game.setAmountQuestions(getValidateNumber());
                System.out.println("Podaj poziom trudności: ");
                game.setLevel(getValidateNumber());
                System.out.print("Czy pytania mają być wymieszane?");
                game.setMixQuestions(yesOrNo());
                System.out.print("Czy odpowiedzi mają być wymieszane?");
                game.setMixAnswers(yesOrNo());
                break;
            } catch (Exception e) {
                System.out.println("Nie poprawna wartość");
            }
        }
    }

    private static Integer getValidateNumber() {
        Integer number;
        while (true) {
            scanner = new Scanner(System.in);
            number = scanner.nextInt();
            if (!(number == null) && number != 0) {
                break;
            }
            System.out.println("Podaj jeszcze raz");
        }
        return number;
    }

    private static boolean yesOrNo() {
        boolean YoN = false;
        System.out.print(" [TAK/NIE]: ");
        while (true) {
            scanner = new Scanner(System.in);
            String yesNo = scanner.nextLine();
            if (!yesNo.isEmpty() && !yesNo.isBlank() && (yesNo.toLowerCase().equals("yes") || yesNo.toLowerCase().equals("no") ||
                    yesNo.toLowerCase().equals("tak") || yesNo.toLowerCase().equals("nie")) ||
                    yesNo.toLowerCase().equals("y") || yesNo.toLowerCase().equals("n") ||
                    yesNo.toLowerCase().equals("t")) {
                if ((yesNo.toLowerCase().equals("yes") || yesNo.toLowerCase().equals("tak")
                        || yesNo.toLowerCase().equals("y")) || yesNo.toLowerCase().equals("t")) {
                    YoN = true;
                }
                break;
            }
            System.out.println("Podaj jeszcze raz");
        }
        return YoN;
    }

    private static void displayQuestions(List<Questions> questionsList) throws InterruptedException {
        // display questions
        for (int i = 0; i < questionsList.size(); i++) {
            // display the questions
            System.out.printf(questSeparator, i + 1, questionsList.get(i).getQuestionText());
            // view available answers
            showAvailableAnswers(questionsList.get(i));
            // retrieving responses from the player
            boolean[] userAnswers = QuestionService.getFromUserCorrectAnswer();
            game.getAllUserAnswers()[(i)] = userAnswers;
            // checking the answer
            boolean answeredCorrect = checkAnswer(questionsList.get(i).getAnswer().getCorrectAnswers(), userAnswers);
            // assigning points for correct answers
            if (answeredCorrect) {
                game.getUser().getUserScore().setHighestScore(game.getUser().getUserScore().getHighestScore()
                        + questionsList.get(i).getScore());
            }
            // next question delay [1 second]
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void showAvailableAnswers(Questions question) {
        int intLetter = (65);
        for (String ans : question.getAnswer().getAnswers()) {
            System.out.print(((char) intLetter) + " : ");
            System.out.println(ans);
            intLetter++;
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
        printMessage(answeredCorrect);
        return answeredCorrect;
    }

    private static void printMessage(boolean isCorrect) {
        System.out.println(isCorrect ? "Twoja odpowiedź jest poprawna!!!" :
                "To jest zła odpowiedź :(");
    }

    private static void finalResult(List<Questions> questionsList) {

        System.out.printf("\t +++ \nUżytkowniku %s", game.getUser().getUserName());
        System.out.printf("\ngratulujemy wyniku: %s pkt \n", game.getUser().getUserScore().getHighestScore());
        System.out.println("Oto tabela wyników: \n " +
                "Odpowiedzi użytkownika podane są w < > nawiasach,\n" +
                "poprawne odpowiedzi podane są w ( ) nawiasach.");
        showLeaderboard(questionsList);
    }

    private static void showLeaderboard(List<Questions> questionsList) {
        for (int i = 0; i < questionsList.size(); i++) {
            System.out.printf(questSeparator, i + 1, questionsList.get(i).getQuestionText());
            // print designation answers
            System.out.println(designationAnswer(questionsList.get(i), i));
        }
    }

    private static StringBuilder designationAnswer(Questions question, int i) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < 4; j++) {
            String out = question.getAnswer().getAnswers()[j];
            // designation correct answer
            out = question.getAnswer().getCorrectAnswers()[j] ? "(" + out + ")" : out;
            // designation user answer
            out = game.getAllUserAnswers()[i][j] ? "<" + out + ">" : out;
            builder.append(out + ", ");
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder;
    }
}