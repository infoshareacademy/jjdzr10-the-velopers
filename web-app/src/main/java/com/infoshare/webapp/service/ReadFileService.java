package com.infoshare.webapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshare.webapp.model.Category;
import com.infoshare.webapp.model.Question;
import com.infoshare.webapp.WebAppApplication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadFileService {

    private static final URL questionResource = WebAppApplication.class.getClassLoader().getResource("questions2.json");

    public ReadFileService() {
    }

    public static List<Question> loadQuestions() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(questionResource, new TypeReference<List<Question>>() {});
    }

    public static List<Question> loadQuestions(Category category) throws IOException {
        return getFilteredQuestions(loadQuestions(),category);
    }

    private static List<Question> getFilteredQuestions(List<Question> questions, Category category){
        List<Question> filteredQuestions = new ArrayList<>();
        for (int i=0; i<questions.size();i++){
            if (questions.get(i).getCategory() == category){
                filteredQuestions.add(questions.get(i));
            }
        }
        return filteredQuestions;
    }
}
