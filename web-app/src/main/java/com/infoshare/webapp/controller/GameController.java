package com.infoshare.webapp.controller;

import com.infoshare.webapp.model.Game;
import com.infoshare.webapp.service.GameService;
import com.infoshare.webapp.service.QuestionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GameController {
    private final GameService gameService;
    private final QuestionService questionService;
    private static final Logger LOGGER = LogManager.getLogger(GameController.class);


    public GameController(GameService gameService, QuestionService questionService) {
        this.questionService = questionService;
        this.gameService = gameService;
    }
    @PostMapping("/create_new_game")
    public String createNewGame(@ModelAttribute Game game, Model model){
        game.setQuestions(questionService.getAllQuestions());
        gameService.settingsGame(game);
        LOGGER.info("Loaded game settings: {}", game);
        return "redirect:/game";
    }
}
