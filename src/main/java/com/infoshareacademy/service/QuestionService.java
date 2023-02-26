package com.infoshareacademy.service;

import com.infoshareacademy.model.Answers;
import com.infoshareacademy.model.Category;
import com.infoshareacademy.model.Questions;

import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class QuestionService {


    public static Questions addQuestion() {
        Questions question = new Questions();
        printCategory();
        question.setCategories(getCategory());
        Answers answer = new Answers();
        question.setQuestionText(getQuestionText());
        answer.setAnswers(getAnswers());
        return question;
    }


    private static void printCategory() {
        System.out.println("Z której kategorii chcesz dodać pytanie?");
        Category[] categories = Category.values();
        for (Category category : categories) {
            System.out.println(category.name());
        }
    }

    private static Category getCategory() {
        Scanner scanner = new Scanner(System.in);
        String userCategory = scanner.nextLine();
        System.out.println(userCategory);
        return Category.valueOf(userCategory);

    }

    private static String getQuestionText() {
        System.out.println("Wpisz pytanie");
        Scanner scanner = new Scanner(System.in);
        String userQuestionText = scanner.nextLine();
        System.out.println(userQuestionText);
        return userQuestionText;
    }

    private static String[] getAnswers() {
        Scanner scanner = new Scanner(System.in);
        String answers[] = new String[4];
        System.out.println("Wpisz odp. nr 1");
        String userAnswer1 = scanner.nextLine();
        System.out.println("Wpisz odp. nr 2");
        String userAnswer2 = scanner.nextLine();
        System.out.println("Wpisz odp. nr 3");
        String userAnswer3 = scanner.nextLine();
        System.out.println("Wpisz odp. nr 4");
        String userAnswer4 = scanner.nextLine();
        answers[0] = userAnswer1;
        answers[1] = userAnswer2;
        answers[2] = userAnswer3;
        answers[3] = userAnswer4;
        return answers;
    }
}














