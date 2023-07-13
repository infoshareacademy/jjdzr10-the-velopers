package com.infoshare.webapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshare.webapp.model.Question;
import com.infoshare.webapp.repository.QuestionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final QuestionRepository questionRepository;
    private static final Logger LOGGER = LogManager.getLogger(FileJSONService.class);

    public FileJSONService(ObjectMapper objectMapper, QuestionRepository questionRepository) {
        this.objectMapper = objectMapper;
        this.questionRepository = questionRepository;
    }

    public void loadFile(MultipartFile file) throws IOException {
        if (Objects.equals(file.getContentType(), "application/json")) {
            List<Question> questions = objectMapper.readValue(new InputStreamReader(file.getInputStream()), new TypeReference<List<Question>>() {});
            questionRepository.saveAll(questions);
            LOGGER.info("Upload questions from file: "+ file.getOriginalFilename());
        }
    }

    public void saveFile(List<Question> questions, String filePath) throws IOException {
        String questionsAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(questions);
        Files.writeString(Path.of(filePath), questionsAsString);
    }
}
