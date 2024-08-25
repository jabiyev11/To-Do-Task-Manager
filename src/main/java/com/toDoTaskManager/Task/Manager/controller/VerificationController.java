package com.toDoTaskManager.Task.Manager.controller;

import com.toDoTaskManager.Task.Manager.entity.User;
import com.toDoTaskManager.Task.Manager.entity.VerificationToken;
import com.toDoTaskManager.Task.Manager.repository.UserRepository;
import com.toDoTaskManager.Task.Manager.service.VerificationTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class VerificationController {

    private VerificationTokenServiceImpl verificationTokenService;
    private UserRepository userRepository;

    @Autowired
    public VerificationController(VerificationTokenServiceImpl verificationTokenService, UserRepository userRepository) {
        this.verificationTokenService = verificationTokenService;
        this.userRepository = userRepository;
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token, Model model) {

        Optional<VerificationToken> verificationToken = verificationTokenService.getToken(token);


        if (verificationToken.isPresent() && !verificationToken.get().isExpired()) {
            User user = verificationToken.get().getUser();
            user.setConfirmed(true);
            userRepository.save(user);
            model.addAttribute("message", "Email verified successfully, You can now Log In");
            model.addAttribute("status", "success");
        } else {
            model.addAttribute("message", "Invalid or expired token");
            model.addAttribute("status", "error");
        }

        return "message";
    }

}
