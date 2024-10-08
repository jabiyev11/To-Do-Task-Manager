package com.toDoTaskManager.Task.Manager.controller;

import com.toDoTaskManager.Task.Manager.entity.Task;
import com.toDoTaskManager.Task.Manager.exceptions.DueDateCannotBeInThePastException;
import com.toDoTaskManager.Task.Manager.service.TaskServiceImpl;
import com.toDoTaskManager.Task.Manager.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskServiceImpl taskService;
    private UserServiceImpl userService;

    @Autowired
    public TaskController(TaskServiceImpl taskService, UserServiceImpl userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllTasks(@RequestParam(value = "sortBy", defaultValue = "dueDate") String sortBy,
                              @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                              Model model) {

        List<Task> tasks = taskService.getSortedTasks(sortBy, sortDirection);
        model.addAttribute("tasks", tasks);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        return "taskList";
    }

    @GetMapping("/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping
    public String saveTask(@ModelAttribute("task") Task task, Model model) {
        try {
            taskService.saveTask(task);
        } catch (DueDateCannotBeInThePastException e) {
            System.out.println("Error during the specification of due date for a task");
            model.addAttribute("message", "Due Date cannot be before the current time");
            model.addAttribute("status", "error");

            return "task-form";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "task-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

}
