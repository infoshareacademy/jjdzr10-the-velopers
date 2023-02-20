package com.infoshareacademy.service;

import com.google.gson.Gson;
import com.infoshareacademy.model.Categories;
import com.infoshareacademy.model.Questions;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class ReadFileService {

    private static Path pathQuestions = Path.of(System.getProperty("user.dir"),"Questions.txt");

    public static Questions[] loadQuestions(Categories categories) throws IOException {
        Questions[] allQuestions = null;
        try {
            // create Reader
            Reader reader = Files.newBufferedReader(pathQuestions);
            System.out.println(pathQuestions);
            System.out.println(reader);
            // create Gson instance
            Gson gson = new Gson();
            // get data from JSON file and convert to Question class
            allQuestions = gson.fromJson(reader, Questions[].class);
            // close reader
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getFiltredQuestions(allQuestions,categories);
    }

    public static Questions[] loadQuestions() throws IOException {
        Questions[] allQuestions = null;
        try {
            // create Reader
            Reader reader = Files.newBufferedReader(pathQuestions);
            System.out.println(pathQuestions);
            System.out.println(reader);
            // create Gson instance
            Gson gson = new Gson();
            // get data from JSON file and convert to Question class
            allQuestions = gson.fromJson(reader, Questions[].class);
            // close reader
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return allQuestions;
    }

    private static Questions[] getFiltredQuestions(Questions[] questions, Categories categories){
        Questions[] filtredQuestions;
        List<Questions> listQuestions = new ArrayList<>();
        for (int i=0; i<questions.length;i++){
            if (questions[i].getCategories() == categories){
                listQuestions.add(questions[i]);
            }
        }
        filtredQuestions = listQuestions.toArray(new Questions[listQuestions.size()]);
        return filtredQuestions;
    }
}
