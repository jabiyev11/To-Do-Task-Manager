package com.toDoTaskManager.Task.Manager.service;

import com.toDoTaskManager.Task.Manager.entity.Task;
import com.toDoTaskManager.Task.Manager.entity.User;
import com.toDoTaskManager.Task.Manager.exceptions.DueDateCannotBeInThePastException;
import com.toDoTaskManager.Task.Manager.repository.TaskRepository;
import com.toDoTaskManager.Task.Manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private UserServiceImpl userService;


    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, UserServiceImpl userService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void saveTask(Task task) {
        task.setUser(userService.getCurrentUser());

        if(task.getDueDate().isBefore(LocalDate.now())){
            throw new DueDateCannotBeInThePastException("Specified due date cannot be before the current date");
        }
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.getTaskById(id);
    }

    @Override
    public List<Task> getSortedTasks(String sortBy, String sortDirection) {
        User currentUser = userService.getCurrentUser();
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        return taskRepository.findByUser(currentUser, sort);
    }
}
