package com.toDoTaskManager.Task.Manager.repository;

import com.toDoTaskManager.Task.Manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

}
