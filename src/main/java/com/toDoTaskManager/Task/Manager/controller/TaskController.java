package com.toDoTaskManager.Task.Manager.controller;

import com.toDoTaskManager.Task.Manager.entity.Task;
import com.toDoTaskManager.Task.Manager.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskServiceImpl taskService;

    @Autowired
    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAllTasks(Model model){
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/new")
    public String showTaskForm(Model model){
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping
    public String saveTask(@ModelAttribute("task") Task task){
         taskService.saveTask(task);
         return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "task-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id){
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
