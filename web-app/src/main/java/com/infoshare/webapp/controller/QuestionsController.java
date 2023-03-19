package com.infoshare.webapp.controller;


import com.infoshare.webapp.model.Questions;
import com.infoshare.webapp.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class QuestionsController {

    private final QuestionService questionService;
    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/questions")
    public List<Questions> getQuestions(Model model) {
        Questions emptyQuestion = new Questions();
        model.addAttribute("question", emptyQuestion);

        List<Questions> cars = questionService.getAll();
        model.addAttribute("question", cars);
//        return "cars/car";
        return cars;
    }

    @GetMapping("/add_question")
    public String addQuestion(Model model) {
        model.addAttribute("pageTitle", "Add question");
        return "add_question";
    }

    @GetMapping("/edit_questions")
    public String editQuestions(Model model) {
        model.addAttribute("pageTitle", "Edit question");
        return "edit_questions";
    }

    @GetMapping("/edit_question_id")
    public String editQuestionId(Model model) {
        model.addAttribute("pageTitle", "Edit question");
        return "edit_question_id";
    }
}