package com.viooh.sandbox.todo.exception;

public class CompletedTaskSaveException  extends RuntimeException{
    public CompletedTaskSaveException(String message) {
        super(message);
    }
}
