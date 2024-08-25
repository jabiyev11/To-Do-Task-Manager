package com.toDoTaskManager.Task.Manager.service;

import com.toDoTaskManager.Task.Manager.entity.User;
import com.toDoTaskManager.Task.Manager.entity.VerificationToken;
import com.toDoTaskManager.Task.Manager.repository.UserRepository;
import com.toDoTaskManager.Task.Manager.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService{

    private VerificationTokenRepository verificationTokenRepository;
    private UserRepository userRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public VerificationToken generateVerificationToken(User user) {

        //Removing old tokens in case of another request
        Set<VerificationToken> oldTokens = user.getVerificationTokens();

        if(oldTokens == null){
            oldTokens = new HashSet<>();

        }
        oldTokens.forEach(token -> verificationTokenRepository.delete(token));


        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);
        VerificationToken verificationToken = new VerificationToken(token, expiryDate, user);

        oldTokens.add(verificationToken);
        user.setVerificationTokens(oldTokens);

        userRepository.save(user);
        return verificationToken;
    }

    @Override
    public boolean validateToken(String token) {

        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);
        if(optionalToken.isPresent()){
            VerificationToken verificationToken = optionalToken.get();
            return verificationToken.getExpiryDate().isAfter(LocalDateTime.now());
        }

        return false;
    }

    @Override
    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
