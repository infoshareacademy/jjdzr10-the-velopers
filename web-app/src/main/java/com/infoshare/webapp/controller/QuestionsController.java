package com.infoshare.webapp.controller;


import com.infoshare.webapp.model.Category;
import com.infoshare.webapp.model.Question;
import com.infoshare.webapp.service.CategoryService;
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

    @GetMapping("/questions")
    public String getQuestions(Model model) {
        model.addAttribute("pageTitle", "Questions List");
        model.addAttribute("questions", questionService.getAllQuestions());
        return "questions_list";
    }

    @GetMapping("/questions/question")
    public String getFormQuestion(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("pageTitle", "Add question");
        List<Category> categories = CategoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "add_question";
    }

    @GetMapping("/questions/delete/{idQuestion}")
    public String deleteQuestion(@PathVariable long idQuestion) {
        questionService.removeQuestionById(idQuestion);
        return "redirect:/questions";
    }

    @GetMapping("/questions/edit/{idQuestion}")
    public String getQuestionById(@PathVariable("idQuestion") long idQuestion, Model model) {
        model.addAttribute("pageTitle", "Edit question");
        Question question = questionService.findById(idQuestion);
        model.addAttribute("question", question);
        List<Category> categories = CategoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "edit_question";
    }

    @GetMapping("questions/{idQuestion}")
    public String getSingleQuestion(@PathVariable("idQuestion") long idQuestion, Model model) {
        model.addAttribute("pageTitle", "Single question");
        model.addAttribute("question", questionService.findById(idQuestion));
        return "question";
    }

    @PostMapping("/questions/edit/{idQuestion}")
    public String editQuestion(@PathVariable("idQuestion") long idQuestion, Question question, Model model) {
        questionService.editQuestion(idQuestion, question);
        return "redirect:/questions";
    }

    @PostMapping("/questions/question")
    public String addQuestion(@ModelAttribute Question question) {
        long questionID = questionService.getLastQuestionId() + 1;
        question.setIdQuestion(questionID);
        questionService.addQuestion(question);
        return "redirect:/questions";
    }
}