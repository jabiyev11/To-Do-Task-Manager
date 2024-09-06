package com.toDoTaskManager.Task.Manager.exceptions;

public class DueDateCannotBeInThePastException extends  RuntimeException{

    public DueDateCannotBeInThePastException(String message) {
        super(message);
    }
}
