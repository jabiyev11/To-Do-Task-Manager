package com.toDoTaskManager.Task.Manager.service;

import com.toDoTaskManager.Task.Manager.entity.Role;

public interface RoleService {

    Role findByName(String roleName);
    Role saveRole(Role role);
}
