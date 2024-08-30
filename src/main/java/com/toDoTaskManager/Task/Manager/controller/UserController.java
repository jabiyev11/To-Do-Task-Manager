package com.toDoTaskManager.Task.Manager.controller;


import com.toDoTaskManager.Task.Manager.entity.User;
import com.toDoTaskManager.Task.Manager.service.EmailService;
import com.toDoTaskManager.Task.Manager.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class UserController {

    private UserServiceImpl userService;
    private EmailService emailService;

    @Autowired
    public UserController(UserServiceImpl userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();

            model.addAttribute("errors", errors);
            return "register";
        }

        User savedUser = userService.registerUser(user);
        emailService.sendVerificationEmail(savedUser);

        model.addAttribute("message", "Registration successful! Please check your email to verify your account");
        model.addAttribute("status", "info");

        return "messageForUser";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/tasks";
    }
}
