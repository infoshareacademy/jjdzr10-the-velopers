package com.infoshare.webapp.controller;

import com.infoshare.webapp.model.User;
import com.infoshare.webapp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {
    private final UserRepository userRepository;
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    public AccountController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/reset_pass")
    public String resetPass(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "reset_password";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("user") User user) {
        LOGGER.info("Register new user: (id:{}) {}", user.getId(), user.getUsername());
        userRepository.save(user);
        return "index";
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register_form";
    }

    @GetMapping("/login")
    public String login() {
            return "login";
    }
}