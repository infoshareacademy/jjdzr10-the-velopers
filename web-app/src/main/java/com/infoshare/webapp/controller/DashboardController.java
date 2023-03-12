package com.infoshare.webapp.controller;

import com.infoshare.webapp.model.GreetingForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String index(Model model) {
        GreetingForm greetingForm = new GreetingForm();
        model.addAttribute("greeting", greetingForm);
        return "index";
    }

    @PostMapping("/greeting")
    public String greeting(@ModelAttribute GreetingForm greeting, Model model) {
        model.addAttribute("name", greeting.getName());
        return "greeting";
    }
}
