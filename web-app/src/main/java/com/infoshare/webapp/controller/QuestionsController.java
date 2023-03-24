package com.infoshare.webapp.controller;


import com.infoshare.webapp.model.Questions;
import com.infoshare.webapp.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class QuestionsController {

    private final QuestionService questionService;
    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/questions_list")
    public String getQuestions(Model model) {
        model.addAttribute("pageTitle", "Questions List");
        Questions emptyQuestion = new Questions();
        model.addAttribute("question", emptyQuestion);

        List<Questions> questionsList = questionService.getAll();
        model.addAttribute("questions", questionsList);
        return "questions_list";
    }
    @GetMapping("/add_question")
    public String addQuestion(Model model) {
        model.addAttribute("question", new Questions());
        model.addAttribute("pageTitle", "Add question");
        return "add_question";
    }
    @GetMapping("questions_list/delete-question/{idQuestion}")
    public String deleteQuestion(@PathVariable long idQuestion) {
        questionService.removeQuestionById(idQuestion);
        return "redirect:/questions_list";
    }
    @GetMapping("/edit_questions")
    public String editQuestions(Model model) {
        model.addAttribute("pageTitle", "Edit question");
        return "edit_questions";
    }
    @GetMapping("/questions_list/{idQuestion}")
    public String getQuestionById(@PathVariable("idQuestion") Long idQuestion, Model model) {
        model.addAttribute("pageTitle", "Edit question");
        Questions question = questionService.findById(idQuestion);
        model.addAttribute("question", question);
        return "edit_question_id";
    }
    @PostMapping("/questions_list/{idQuestion}/edit_questions")
    public String editQuestion(@PathVariable("idQuestion") Long idQuestion, Questions questions, Model model) {
        questionService.editQuestionById(idQuestion, questions);
        return "redirect:/questions_list";
    }
    @PostMapping("/form_question")
    public String formQuestion(@ModelAttribute Questions question){
        questionService.addQuestion(question);
        return "redirect:/questions_list";
    }
}