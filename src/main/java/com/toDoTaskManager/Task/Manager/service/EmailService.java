package com.toDoTaskManager.Task.Manager.service;

import com.toDoTaskManager.Task.Manager.entity.User;
import com.toDoTaskManager.Task.Manager.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;
    private VerificationTokenServiceImpl verificationTokenService;

    @Autowired
    public EmailService(JavaMailSender mailSender, VerificationTokenServiceImpl verificationTokenService) {
        this.mailSender = mailSender;
        this.verificationTokenService = verificationTokenService;
    }

    public void sendVerificationEmail(User user){
        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(user);
        String token = verificationToken.getToken();

        String verificationUrl = "http://localhost:8080/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Email Verification");
        message.setText("Click the following link to verify your email: " + verificationUrl);

        mailSender.send(message);
    }
}
