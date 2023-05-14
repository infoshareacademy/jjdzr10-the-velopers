package com.infoshare.webapp.controller;

import com.infoshare.webapp.model.Answers;
import com.infoshare.webapp.model.Game;
import com.infoshare.webapp.model.Questions;
import com.infoshare.webapp.service.GameService;
import com.infoshare.webapp.service.QuestionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    @GetMapping("/game")
    public String gamePlay(Model model){
        List<Questions> questions = gameService.getAllQuestions();
        model.addAttribute("questions", questions);

        Questions question = questions.iterator().next();
        LOGGER.info("Find first question to display : {} ", question);

        model.addAttribute("question", question);
        Answers answers = gameService.getUserAnswer(question);
        model.addAttribute("userAnswer",answers);
        return "/game_play";
    }
    @GetMapping("/game/{idQuestion}")
    public String showQuestion(@PathVariable("idQuestion") long idQuestion, Model model){
        List<Questions> questions = gameService.getAllQuestions();
        model.addAttribute("questions", questions);

        Questions question = questionService.findById(idQuestion);
        LOGGER.info("Loaded question {}", question);
        model.addAttribute("question", question);

        Answers answers = gameService.getUserAnswer(question);
        LOGGER.info("GET user answers from list : {}", answers.getCorrectAnswers());
        model.addAttribute("userAnswer", answers);
        boolean correction = gameService.compareAnswers(question.getAnswer(), answers);
        model.addAttribute("correction", correction);
        return "/game_play";
    }
    @PostMapping("/game/{idQuestion}")
    public String sendAnswer(@ModelAttribute Answers userAnswer, @PathVariable long idQuestion){
        Questions question = questionService.findById(idQuestion);
        LOGGER.info("GET User input: {}", userAnswer.getCorrectAnswers());
        List<Boolean> userListAnswers = gameService.makeListUserAnswers(question.getAnswer(),userAnswer.getCorrectAnswers());
        Answers userAnswers = new Answers();
        userAnswers.setCorrectAnswers(userListAnswers);
        LOGGER.info("Converted answers: {}", userAnswers.getCorrectAnswers());
        Boolean isCorrectAnswer = gameService.compareAnswers(question.getAnswer(), userAnswers);
        LOGGER.info("Check user answer: {}", isCorrectAnswer);
        gameService.setUserAnswer(question, userAnswers);
        return "redirect:/game/{idQuestion}";
    }

}
