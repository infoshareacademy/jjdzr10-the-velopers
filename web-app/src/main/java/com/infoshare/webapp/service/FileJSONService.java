package com.infoshare.webapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshare.webapp.model.Question;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Service
public class FileJSONService {
    private final ObjectMapper objectMapper;
    private List<Question> questions;

    public FileJSONService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Question> getQuestions(){ return questions;}
    public void loadFile(MultipartFile file) throws IOException {
        if (Objects.equals(file.getContentType(), "application/json")) {
            questions = objectMapper.readValue(new InputStreamReader(file.getInputStream()), new TypeReference<List<Question>>() {});
        }
    }

    public void saveFile(List<Question> questions, String filePath) throws IOException {
        String questionsAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(questions);
        Files.writeString(Path.of(filePath), questionsAsString);
    }
}
