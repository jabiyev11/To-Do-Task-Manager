package com.toDoTaskManager.Task.Manager.repository;

import com.toDoTaskManager.Task.Manager.entity.Task;
import com.toDoTaskManager.Task.Manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    Task getTaskById(Long id);
}
