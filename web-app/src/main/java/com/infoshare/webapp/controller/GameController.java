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

import javax.servlet.http.HttpSession;
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
    public String createNewGame(@ModelAttribute UserGame game, Model model, RedirectAttributes redirectAttributes) throws Exception {
        game.setGameQuestions(questionService.getAllQuestions());
        gameService.settingsGame(game);
        LOGGER.info("Loaded game settings: {}", game);

        if (game.getGameQuestions().size() <= 0 ){
            redirectAttributes.addFlashAttribute("message", "Question list is empty!");
            redirectAttributes.addFlashAttribute("messageType","success");
            return "redirect:/new_game";
        }
        int firstQuestionIndex = 0;
        redirectAttributes.addAttribute("index",firstQuestionIndex);
        return "redirect:/game/{index}";
    }

    @GetMapping("/game/{index}")
    public String showQuestion(@PathVariable("index") int index, Model model, HttpSession session) throws Exception {
        List<Question> questions = gameService.getAllQuestions();
        model.addAttribute("questions", questions);

        Question question = gameService.getQuestionByIndex(index);
        LOGGER.info("Loaded question : {} ", question);
        model.addAttribute("question", question);

        session.setAttribute("questionIndex", questions.indexOf(question));
        List<Boolean> userAnswered = gameService.userAnsweredList();
        AnswerDto answer = gameService.getUserAnswer(question);
        model.addAttribute("userAnswered",userAnswered);
        model.addAttribute("userAnswer",answer);
        model.addAttribute("currentQuestionIndex", index);
        model.addAttribute("questionCount", questions.size());

        return "/game_play";
    }
    @PostMapping("/game/{index}")
    public String sendAnswer(@ModelAttribute AnswerDto userAnswer, @PathVariable int index, HttpSession session){
        int questionIndex = (int) session.getAttribute("questionIndex");
        Question question = gameService.getAllQuestions().get(questionIndex);
        LOGGER.info("GET User input: {}", userAnswer.getAnswers());
        gameService.setUserAnswer(question, userAnswer);

        return "redirect:/game/{index}";
    }
    @GetMapping("/game/result")
    public String gameResult(Model model) {
        model.addAttribute("message", "Congrats! You earn: "+ gameService.calculateScore() +" points");
        model.addAttribute("messageType","success");
        model.addAttribute("questions", gameService.getAllQuestions());
        model.addAttribute("userAnswer", gameService.getAllUserAnswer());
        model.addAttribute("isCorrect", gameService.userAnsweredCorrectList());
        model.addAttribute("score", gameService.calculateScore());
        return "/result";
    }
}
