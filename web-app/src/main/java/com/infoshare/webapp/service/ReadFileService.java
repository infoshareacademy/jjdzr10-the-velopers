package com.infoshare.webapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshare.webapp.model.Category;
import com.infoshare.webapp.model.Questions;
import com.infoshare.webapp.WebAppApplication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadFileService {

    private static final URL questionResource = WebAppApplication.class.getClassLoader().getResource("questions.json");

    public ReadFileService() {
    }

    public static List<Questions> loadQuestions() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Questions> questionsList = objectMapper.readValue(questionResource, new TypeReference<List<Questions>>() {});
        return questionsList;
    }

    public static List<Questions> loadQuestions(Category category) throws IOException {
        return getFilteredQuestions(loadQuestions(),category);
    }

    private static List<Questions> getFilteredQuestions(List<Questions> questions, Category category){
        List<Questions> filteredQuestions = new ArrayList<>();
        for (int i=0; i<questions.size();i++){
            if (questions.get(i).getCategory() == category){
                filteredQuestions.add(questions.get(i));
            }
        }
        return filteredQuestions;
    }
}
