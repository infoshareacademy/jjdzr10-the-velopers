package com.infoshare.webapp.controller;

import com.infoshare.webapp.Dto.UserDto;
import com.infoshare.webapp.exception.UserAlreadyExistException;
import com.infoshare.webapp.exception.UserNotCompareToEmailException;
import com.infoshare.webapp.exception.UserNotExistException;
import com.infoshare.webapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.event.Level;
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

    @GetMapping("/reset_password")
    public String resetPasswordForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "reset_password";
    }

    @PostMapping("/reset_password")
    public String resetPassword(@ModelAttribute("user") UserDto userDto, RedirectAttributes redirectAttributes) {
        try {
            userService.resetPassword(userDto);
            redirectAttributes.addFlashAttribute("message", "We send e-mail to reset your password. Check your mail.");
            redirectAttributes.addFlashAttribute("messageType","success");
            return "redirect:/";
        }
        catch (UserNotExistException e){
            LOGGER.error("wystapil blad", e);
            redirectAttributes.addFlashAttribute("message", "Used e-mail do not exist!");
            redirectAttributes.addFlashAttribute("messageType","danger");
            return "redirect:/reset_password";
        }
        catch (UserNotCompareToEmailException e){
            redirectAttributes.addFlashAttribute("message", "This email address: "+ userDto.getEmail() + " does not match to user name!");
            redirectAttributes.addFlashAttribute("messageType","warning");
            return "redirect:/reset_password";
        }
    }

    @PostMapping("/register")
    public String registrationUserAccount(@ModelAttribute("user") @Valid UserDto userDto, RedirectAttributes redirectAttributes) {
        try {
            userService.registerNewUserAccount(userDto);
            LOGGER.info("Register new user: {}", userDto.getUsername());
        } catch (UserAlreadyExistException e) {
            redirectAttributes.addFlashAttribute("message", "Used e-mail already exist!");
            redirectAttributes.addFlashAttribute("messageType", "danger");
            LOGGER.info("Registration of new user has failed - e-mail already exists.");
            return "redirect:/register";
        }
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        LOGGER.info("register");
        LOGGER.debug("Model: " + model);
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register_form";
    }

    @GetMapping("/login")
    public String login() {
        LOGGER.info("logging in...");
        return "login";
    }
}