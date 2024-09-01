package com.toDoTaskManager.Task.Manager.exceptions;

public class EmailNotConfirmedException extends RuntimeException{

    private String message;


    public EmailNotConfirmedException(String message){
        super(message);
    }

}
