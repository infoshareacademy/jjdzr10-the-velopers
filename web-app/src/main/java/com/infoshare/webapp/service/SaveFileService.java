package com.infoshare.webapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshare.webapp.WebAppApplication;
import com.infoshare.webapp.model.Questions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SaveFileService {

    private static URL questionResource = WebAppApplication.class.getClassLoader().getResource("questions.json");
    public static void saveQuestionsToFile(List<Questions> questions) throws IOException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        String questionsAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(questions);
        System.out.println("Plik \"" + Path.of(questionResource.toURI()).getFileName() +
                    "\" zapisany z powodzeniem \nw podanej lokalizacji: \n" + questionResource.getFile());
        Files.writeString(Path.of(questionResource.getPath()),questionsAsString);
    }
    public static List<Questions> glueQuestionsList(List<Questions> questions, List<Questions> questionsList){
        for (Questions quest:questions) {
            questionsList.add(quest);
        }
        return questionsList;
    }
}