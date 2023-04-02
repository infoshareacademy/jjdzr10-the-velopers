package com.infoshare.webapp.service;

import com.infoshare.webapp.exception.QuestionsNotFoundException;
import com.infoshare.webapp.model.Answers;
import com.infoshare.webapp.model.Category;
import com.infoshare.webapp.model.Questions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

@Service
public class QuestionService {

    private static List<Long> availableQuestionNumbers;
    private static Scanner scanner;
    private static List<Questions> questionsList;
    private final ReadFileService readFileService;

    public QuestionService(ReadFileService readFileService) throws IOException {
        this.readFileService = readFileService;
        questionsList = this.readFileService.loadQuestions();
    }

    public List<Questions> getAllQuestions() {
        return questionsList;
    }

    public void editQuestion(Long id, Questions question) {
        Questions questionToEdit = findById(id);
        questionToEdit.setAnswer(question.getAnswer());
        questionToEdit.setCategory(question.getCategory());
        questionToEdit.setQuestionText(question.getQuestionText());
        questionToEdit.setScore(question.getScore());
    }

    public Questions findById(Long id) {
        return questionsList.stream()
                .filter(question -> question.getIdQuestion() == id)
                .findFirst()
                .orElseThrow(() -> new QuestionsNotFoundException(String.format("Not found question with given Id:  %S", id)));
    }

    public void removeQuestionById(Long id) {
        Questions foundQuestion = findById(id);
        questionsList.remove(foundQuestion);
    }

    public void addQuestion(Questions question) {
        questionsList.add(question);
    }

    private static Questions formQuestion() {
        Questions question = new Questions();
        Answers answer = new Answers();
        // category
        printCategory();
        question.setCategory(getFromUserCategory());
        // question number
        question.setIdQuestion(getFromUserQuestionNumber());
        // question text
        question.setQuestionText(getFromUserQuestionText());
        // answers
        answer.setAnswers(getFromUserAnswers());
        answer.setCorrectAnswers(getFromUserCorrectAnswer());
        question.setAnswer(answer);
        // score
        question.setScore(getFromUserQuestionScore());
        return question;
    }

    public static void addQuestion(List<Questions> questionsList) throws IOException, URISyntaxException {
        Questions newQuestion = formQuestion();
        questionsList.add(newQuestion);
        SaveFileService.saveQuestionsToFile(questionsList);
    }

    public static void editQuestion(List<Questions> questionsList) throws IOException, URISyntaxException {
        // show available questions
        availableQuestions(questionsList);
        // choose question to edit
        Questions quest = chooseQuestion(questionsList);
        showQuestion(quest);
        // make question
        Questions editQuestion = formQuestion();
        // remove chosen question from questionList
        questionsList.remove(quest);
        // add new question to questionList
        questionsList.add(editQuestion);
        // save to file
        SaveFileService.saveQuestionsToFile(questionsList);
    }

    public static void removeQuestion(List<Questions> questionsList) throws IOException {
        // show available questions
        availableQuestions(questionsList);
        // choose question to edit
        Questions questionToRemove = chooseQuestion(questionsList);
        showQuestion(questionToRemove);
        questionsList.remove(questionToRemove);
    }

    private static void availableQuestions(List<Questions> questionsList) {
        for (Category cat : Category.values()) {
            System.out.println("Kategoria: " + cat);
            System.out.print("Dostępne numery pytań: ");
            StringBuilder stringBuilder = new StringBuilder();

            questionsList.stream().filter(c -> c.getCategory().equals(cat)).forEach(s -> stringBuilder.append(s.getIdQuestion() + ", "));
            System.out.println(stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(",")));
        }
        // return all possible number question to choose
        availableQuestionNumbers = questionsList.stream().map(Questions::getIdQuestion).toList();
    }

    private static void showQuestion(Questions questions) {
        System.out.println("Wybrałeś pytanie: ");
        System.out.println(questions.toString());
    }

    private static Questions chooseQuestion(List<Questions> questions) {
        System.out.println("Wybierz numer pytania: ");
        Long chosenIdQuestion;
        while (true) {
            chosenIdQuestion = getValidateNumber();
            if (availableQuestionNumbers.contains(chosenIdQuestion)) {
                break;
            }
            System.out.println("Wartość jest z poza zakresu. Podaj jeszcze raz.");
        }
        Long finalChosenIdQuestion = chosenIdQuestion;
        Questions question = questions.stream().filter(q -> finalChosenIdQuestion.equals(q.getIdQuestion())).findFirst().get();
        return question;
    }

    private static Long getValidateNumber() {
        Long number;
        while (true) {
            scanner = new Scanner(System.in);
            number = scanner.nextLong();
            if (!(number == null) && number != 0) {
                break;
            }
            System.out.println("Podaj jeszcze raz");
        }
        return number;
    }

    private static void printCategory() {
        System.out.println("Podaj kategorie pytania:");
        Category[] categories = Category.values();
        for (Category category : categories) {
            System.out.println(category.name());
        }
    }

    private static Category getFromUserCategory() {
        scanner = new Scanner(System.in);
        String userCategory = scanner.nextLine();
        System.out.println(userCategory);
        return Category.valueOf(userCategory.toUpperCase());
    }

    private static Long getFromUserQuestionNumber() {
        System.out.println("Podaj numer pytania: ");
        scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    private static Integer getFromUserQuestionScore() {
        System.out.println("Ile punktów za poprawną odpowiedź na to pytanie:");
        scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static String getFromUserQuestionText() {
        System.out.println("Wpisz pytanie");
        scanner = new Scanner(System.in);
        String userQuestionText = scanner.nextLine();
        System.out.println(userQuestionText);
        return userQuestionText;
    }

    private static String[] getFromUserAnswers() {
        scanner = new Scanner(System.in);
        String answers[] = new String[4];
        for (int i = 0; i < answers.length; i++) {
            System.out.print("Podaj odpowiedź " + ((char) (i + 65)) + " : ");
            answers[i] = scanner.nextLine();
        }
        return answers;
    }

    static boolean[] getFromUserCorrectAnswer() {
        scanner = new Scanner(System.in);
        boolean[] userAnswerBoolean = new boolean[4];

        System.out.print("Jaka jest poprawna odpowiedź?: ");
        boolean isCorecct = false;
        while (!isCorecct) {
            String userAnswers = scanner.nextLine().toUpperCase();
            // TODO zabezpieczy przed podaniem odpowiedzi w postaci: "A,Z" . Gdy jedna litera jest A,B,C,D i podajemy jeszcze inna z poza tego zakresu
            if (userAnswers.contains("A") || userAnswers.contains("B") || userAnswers.contains("C") || userAnswers.contains("D")) {
                userAnswerBoolean = convertAnswer(userAnswers);
                isCorecct = true;
            } else {
                System.out.print("Podaj jeszcze raz odpowiedź z zakresu: A,B,C,D :");
            }
        }
        return userAnswerBoolean;
    }

    private static boolean[] convertAnswer(String userAnswer) {
        String[] splitUserAnswers = userAnswer.split(",", 4);
        boolean[] booleansAnswers = {false, false, false, false};
        for (int i = 0; i < splitUserAnswers.length; i++) {
            switch (splitUserAnswers[i]) {
                case "A":
                    booleansAnswers[0] = true;
                    break;
                case "B":
                    booleansAnswers[1] = true;
                    break;
                case "C":
                    booleansAnswers[2] = true;
                    break;
                case "D":
                    booleansAnswers[3] = true;
                    break;
            }
        }
        return booleansAnswers;
    }
}














