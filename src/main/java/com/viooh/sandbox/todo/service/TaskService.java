package com.viooh.sandbox.todo.service;

import com.viooh.sandbox.todo.exception.CompletedTaskSaveException;
import com.viooh.sandbox.todo.exception.TaskAlreadyClosedException;
import com.viooh.sandbox.todo.exception.TaskNotFoundException;
import com.viooh.sandbox.todo.model.Task;
import com.viooh.sandbox.todo.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Optional<Task> getTaskById(Long id){
        return taskRepository.findOneById(id);
    }

    public Optional<Task> getTaskByTitle(String title){
        return taskRepository.findOneByTitle(title);
    }

    public Task createTask(Task task) {
        if(task.isCompleted() && task.getId() == null){
            throw new CompletedTaskSaveException("Completed Task cannot be created!");
        }

        return taskRepository.save(task);
    }

    public Task closeTask(Long id) {
        Optional<Task> getTaskById = taskRepository.findOneById(id);
        if(!getTaskById.isPresent()){
            throw new TaskNotFoundException("The task with id:" + id + " could not be found!");
        } else {
            Task task = getTaskById.get();
            if (task.isCompleted()) {
                throw new TaskAlreadyClosedException("Task id" + id + " is already closed!");
            } else {
                task.setCompleted(true);
                return taskRepository.save(task);
            }
        }
    }
}
