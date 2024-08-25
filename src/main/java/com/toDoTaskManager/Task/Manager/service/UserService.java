package com.toDoTaskManager.Task.Manager.service;

import com.toDoTaskManager.Task.Manager.entity.Task;
import com.toDoTaskManager.Task.Manager.entity.User;
import com.toDoTaskManager.Task.Manager.repository.UserRepository;


public interface UserService {


    User registerUser(User user);

    User findUserByUsername(String username);


}
