package com.viooh.sandbox.todo.exception;

public class TaskAlreadyClosedException extends RuntimeException{
    public TaskAlreadyClosedException(String message) {
        super(message);
    }
}
