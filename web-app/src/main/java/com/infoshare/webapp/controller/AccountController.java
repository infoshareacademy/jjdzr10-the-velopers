package com.infoshare.webapp.controller;

import com.infoshare.webapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AccountController {
    @GetMapping("/reset_pass")
    public String resetPass(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "reset_password";
    }

    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("user") User user){
        System.out.println(user);
        return "index";
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user",user);
        return "register_form";
    }
}