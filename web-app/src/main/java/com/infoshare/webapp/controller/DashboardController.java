package com.infoshare.webapp.controller;


import com.infoshare.webapp.model.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DashboardController {

    private static final Logger LOGGER = LogManager.getLogger(DashboardController.class);

    @GetMapping("/")
    public String index(Model model) {
        LOGGER.info("Main page");
        model.addAttribute("pageTitle", "Home");
        return "index";
    }



    @GetMapping("/new_game")
    public String newGame(Model model) {
        LOGGER.info("New game has been triggered");
        model.addAttribute("pageTitle", "New game");
        model.addAttribute("game", new Game());
        return "new_game";
    }



}
