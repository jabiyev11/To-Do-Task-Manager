package com.toDoTaskManager.Task.Manager.repository;

import com.toDoTaskManager.Task.Manager.entity.User;
import com.toDoTaskManager.Task.Manager.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
}
