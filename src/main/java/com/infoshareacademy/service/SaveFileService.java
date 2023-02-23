package com.infoshareacademy.service;

import com.google.gson.Gson;
import com.infoshareacademy.model.Questions;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SaveFileService {
    private static Path pathQuestions = Path.of(System.getProperty("user.dir"),"Questions.txt");
    private static Gson gson = new Gson();

    public static void addQuestionsToFile(List<Questions> questions) {
        try {
            // get questions from file
            List<Questions> allQuestions = ReadFileService.loadQuestions();

            // add questions to questions from file
            List<Questions> questionsToFile = new ArrayList<>();
            questionsToFile = addQuestions(allQuestions,questionsToFile);
            questionsToFile = addQuestions(questions,questionsToFile);

            // create a writer
            Writer writer = Files.newBufferedWriter(pathQuestions);
            // convert questions object to JSON file
            gson.toJson(questionsToFile, writer);
            // close writer
            writer.close();

            System.out.println("Plik " + pathQuestions.getFileName() +
                    " zapisany z powodzeniem \nw podanej lokalizacji: \n" + pathQuestions);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    private static List<Questions> addQuestions(List<Questions> questions, List<Questions> questionsList){
        for (Questions quest:questions) {
            questionsList.add(quest);
        }
        return questionsList;
    }
}