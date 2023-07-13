package com.infoshareacademy.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infoshareacademy.App;
import com.infoshareacademy.model.Category;
import com.infoshareacademy.model.Questions;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class ReadFileService {
    private static final URL pathQuestions = App.class.getClassLoader().getResource("questions.json");
//    private static Path pathQuestions = Path.of(System.getProperty("user.dir"),"src","main","resources","questions.json");
    static Gson gson = new Gson();

    public static List<Questions> loadQuestions(Category category) throws IOException {
        List<Questions> allQuestions = new ArrayList<>();
        try {
            // create Reader
            Reader reader = Files.newBufferedReader(Path.of(pathQuestions.toURI()));
            System.out.println(pathQuestions);
            System.out.println(reader);

            // for converting JSON array to a List, we need to use TypeToken class
            Type userListType = new TypeToken<ArrayList<Questions>>(){}.getType();
            // get data from JSON file and convert to Question class
            allQuestions = gson.fromJson(reader, userListType);
            // close reader
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getFilteredQuestions(allQuestions, category);
    }

    public static List<Questions> loadQuestions() throws IOException {
        List<Questions> allQuestions = new ArrayList<>();
        try {
            // create Reader
            Reader reader = Files.newBufferedReader(Path.of(pathQuestions.toURI()));
            System.out.println(pathQuestions);
            // create Gson instance
            Gson gson = new Gson();
            // for converting such JSON array to a List, we need to use TypeToken class
            Type userListType = new TypeToken<ArrayList<Questions>>(){}.getType();
            // get data from JSON file and convert to Question class
            allQuestions = gson.fromJson(reader, userListType);
            // close reader
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return allQuestions;
    }

    private static List<Questions> getFilteredQuestions(List<Questions> questions, Category category){
        List<Questions> filteredQuestions = new ArrayList<>();
        for (int i=0; i<questions.size();i++){
            if (questions.get(i).getCategories() == category){
                filteredQuestions.add(questions.get(i));
            }
        }
        return filteredQuestions;
    }
}
