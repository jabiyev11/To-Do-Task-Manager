package com.toDoTaskManager.Task.Manager.service;

import com.toDoTaskManager.Task.Manager.entity.Role;
import com.toDoTaskManager.Task.Manager.entity.Task;
import com.toDoTaskManager.Task.Manager.entity.User;
import com.toDoTaskManager.Task.Manager.exceptions.UserAlreadyExistsException;
import com.toDoTaskManager.Task.Manager.repository.RoleRepository;
import com.toDoTaskManager.Task.Manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User registerUser(User user) {

        User existingUser = userRepository.findByEmail(user.getEmail());

        if(existingUser != null){
            if(existingUser.isConfirmed()){
                throw new UserAlreadyExistsException("User already registered. Please log in");
            }
            else{
                emailService.sendVerificationEmail(existingUser);
                throw new UserAlreadyExistsException("User already registered but not confirmed. Confirmation email re-sent, check your email");
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName("User");
        if(userRole == null){
            userRole = new Role();
            userRole.setName("User");
            roleRepository.save(userRole);
        }
        user.setRole(userRole);
        return userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    public User getCurrentUser(){
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return user;
    }

}
