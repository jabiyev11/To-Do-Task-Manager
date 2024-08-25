package com.toDoTaskManager.Task.Manager.service;

import com.toDoTaskManager.Task.Manager.entity.User;
import com.toDoTaskManager.Task.Manager.entity.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    VerificationToken generateVerificationToken(User user);
    boolean validateToken(String token);

    Optional<VerificationToken> getToken(String token);


}
