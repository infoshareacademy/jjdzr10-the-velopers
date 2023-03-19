package com.infoshare.webapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DashboardController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Home");
        return "index";
    }



    @GetMapping("/new_game")
    public String newGame(Model model) {
        model.addAttribute("pageTitle", "New game");
        return "new_game";
    }




}
