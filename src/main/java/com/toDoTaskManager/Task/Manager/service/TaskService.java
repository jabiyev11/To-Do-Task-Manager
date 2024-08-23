package com.toDoTaskManager.Task.Manager.service;

import com.toDoTaskManager.Task.Manager.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    Task saveTask(Task task);

    void deleteTask(Long id);

    Task getTaskById(Long id);

}
