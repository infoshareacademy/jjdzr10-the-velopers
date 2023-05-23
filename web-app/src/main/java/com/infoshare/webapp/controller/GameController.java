package com.infoshare.webapp.controller;

import com.infoshare.webapp.Dto.AnswerDto;
import com.infoshare.webapp.model.Game;
import com.infoshare.webapp.model.Question;
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
    @PostMapping("/create_new_game")
    public String createNewGame(@ModelAttribute Game game, Model model, RedirectAttributes redirectAttributes){
        game.setQuestions(questionService.getAllQuestions());
        gameService.settingsGame(game);
        LOGGER.info("Loaded game settings: {}", game);
        long idQuestion = gameService.getFirstQuestionId();
        if (idQuestion<=0){
            redirectAttributes.addFlashAttribute("message", "Question list is empty!");
            redirectAttributes.addFlashAttribute("messageType","danger");
            return "redirect:/new_game";
        }
        redirectAttributes.addAttribute("idQuestion",idQuestion);
        return "redirect:/game/{idQuestion}";
    }

    @GetMapping("/game/{idQuestion}")
    public String showQuestion(@PathVariable("idQuestion") long idQuestion, Model model){
        List<Question> questions = gameService.getAllQuestions();
        model.addAttribute("questions", questions);

        Question question = questionService.findById(idQuestion);
        LOGGER.info("Loaded question : {} ", question);
        model.addAttribute("question", question);


        AnswerDto answer = gameService.getUserAnswer(question);

        model.addAttribute("userAnswer",answer);
        boolean correction = true;
        model.addAttribute("correction", correction);
        return "/game_play";
    }
    @PostMapping("/game/{idQuestion}")
    public String sendAnswer(@ModelAttribute AnswerDto userAnswer, @PathVariable long idQuestion){
        Question question = questionService.findById(idQuestion);
        LOGGER.info("GET User input: {}", userAnswer.getAnswers());
        gameService.setUserAnswer(question, userAnswer);

        return "redirect:/game/{idQuestion}";
    }

}
