package com.infoshare.webapp.controller;

import com.infoshare.webapp.Dto.UserDto;
import com.infoshare.webapp.exception.UserAlreadyExistException;
import com.infoshare.webapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AccountController {
    private final UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reset_pass")
    public String resetPass(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "reset_password";
    }

    @PostMapping("/register")
    public String registrationUserAccount(@ModelAttribute("user") @Valid UserDto userDto, RedirectAttributes redirectAttributes) {
        try {
            userService.registerNewUserAccount(userDto);
            LOGGER.info("Register new user: {}", userDto.getUsername());
        }
            catch (UserAlreadyExistException e){
                redirectAttributes.addFlashAttribute("message", "Used e-mail already exist!");
                redirectAttributes.addFlashAttribute("messageType","danger");
                return "redirect:/register";
            }
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register_form";
    }

    @GetMapping("/login")
    public String login() {
            return "login";
    }
}