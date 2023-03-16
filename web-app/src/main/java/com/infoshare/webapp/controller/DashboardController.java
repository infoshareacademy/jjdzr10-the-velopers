package com.infoshare.webapp.controller;

import com.infoshare.webapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "register_form";
    }

    @GetMapping("/new_game")
    public String newGame() {
        return "new_game";
    }

    @GetMapping("/edit_questions")
    public String editQuestions() {
        return "edit_questions";
    }

    @GetMapping("/reset_pass")
    public String resetPass(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "reset_password";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("user") User user){
        System.out.println(user);
        return "start";
    }
}
