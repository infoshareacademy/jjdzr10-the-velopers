package com.infoshare.webapp.service;

import com.infoshare.webapp.model.Questions;
import com.infoshare.webapp.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuService {

    private static User currentUser;

    public static void mainMenu() {
        if (currentUser == null) {userMenu();}
        int menuOption;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz opcje:");
        System.out.println("1 - nowa gra");
        System.out.println("2 - zarządzaj pytaniami");
        System.out.println("3 - wyjście");
        try {
            menuOption = scanner.nextInt();
            switch (menuOption) {
                case 1:
                    //nowa gra
                    gameMenu();
                    break;
                case 2:
                    //zarządzaj pytaniami
                    editMenu();
                    break;
                case 3:
                    //wyjście
                    System.out.println("Dzięki za wspólną zabawę!");
                    break;
                default:
                    mainMenuError();
            }
        } catch (Exception e) {
            mainMenuError();
        }
    }

    static void mainMenuError() {
        System.out.println("Nieprawidłowa opcja!");
        mainMenu();
    }


    public static void editMenu() {
        int menuOption;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz opcje:");
        System.out.println("1 - dodaj pytanie");
        System.out.println("2 - usuń pytanie");
        System.out.println("3 - edytuj pytanie");
        System.out.println("4 - powrót do menu");
        try {
            menuOption = scanner.nextInt();
            switch (menuOption) {
                case 1:
                    //dodaj pytanie
                    QuestionService.addQuestion(ReadFileService.loadQuestions());
                    editMenu();
                    break;
                case 2:
                    //usuń pytanie
                    QuestionService.removeQuestion(ReadFileService.loadQuestions());
                    editMenu();
                    break;
                case 3:
                    //edytuj pytanie
                    QuestionService.editQuestion(ReadFileService.loadQuestions());
                    editMenu();
                    break;
                case 4:
                    //wróć do menu głównego
                    mainMenu();
                    break;
                default:
                    editMenuError();
            }
        } catch (Exception e) {
            editMenuError();
        }
    }

    static void editMenuError() {
        System.out.println("Nieprawidłowa opcja!");
        editMenu();
    }

    private static void userMenu(){
        currentUser = UserService.createUser();
    }

    private static void gameMenu() throws InterruptedException, IOException {
        new GameService(ReadFileService.loadQuestions(), currentUser);
        mainMenu();
    }
}
