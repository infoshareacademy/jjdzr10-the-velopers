package com.infoshare.webapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class QuestionsController {
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