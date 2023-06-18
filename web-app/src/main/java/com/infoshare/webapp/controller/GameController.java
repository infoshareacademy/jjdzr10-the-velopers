package com.infoshare.webapp.controller;

import com.infoshare.webapp.Dto.AnswerDto;
import com.infoshare.webapp.model.Question;
import com.infoshare.webapp.model.UserGame;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @PostMapping("/game/create")
    public String createNewGame(@ModelAttribute UserGame game, Model model, RedirectAttributes redirectAttributes){
        game.setGameQuestions(questionService.getAllQuestions());
        gameService.settingsGame(game);
        LOGGER.info("Loaded game settings: {}", game);
        long idQuestion = gameService.getFirstQuestionId();
        if (idQuestion<=0){
            redirectAttributes.addFlashAttribute("message", "Question list is empty!");
            redirectAttributes.addFlashAttribute("messageType","danger");
            return "redirect:/new_game";
        }
        redirectAttributes.addAttribute("id",idQuestion);
        return "redirect:/game/{id}";
    }

    @GetMapping("/game/{id}")
    public String showQuestion(@PathVariable("id") long id, Model model){
        List<Question> questions = gameService.getAllQuestions();
        model.addAttribute("questions", questions);

        Question question = questionService.findById(id);
        LOGGER.info("Loaded question : {} ", question);
        model.addAttribute("question", question);


        AnswerDto answer = gameService.getUserAnswer(question);

        model.addAttribute("userAnswer",answer);
        boolean correction = true;
        model.addAttribute("correction", correction);
        return "/game_play";
    }
    @PostMapping("/game/{id}")
    public String sendAnswer(@ModelAttribute AnswerDto userAnswer, @PathVariable long id){
        Question question = questionService.findById(id);
        LOGGER.info("GET User input: {}", userAnswer.getAnswers());
        gameService.setUserAnswer(question, userAnswer);

        return "redirect:/game/{id}";
    }
    @GetMapping("/game/result")
    public String gameResult(Model model) {
        model.addAttribute("questions", gameService.getAllQuestions());
        model.addAttribute("userAnswer", gameService.getAllUserAnswer());
        model.addAttribute("isCorrect", gameService.getUserCorrect());
        model.addAttribute("score", gameService.calculateScore());
        return "/result";
    }
}
