package com.viooh.sandbox.todo.controller;

import com.viooh.sandbox.todo.model.Task;
import com.viooh.sandbox.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id){
        return Optional
                .ofNullable(taskService.getTaskById(Long.parseLong(id)))
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/close")
    public ResponseEntity<?> closeTask(@PathVariable String id){
        return Optional
                .ofNullable(taskService.getTaskById(Long.parseLong(id)))
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //TODO: add query by title
}
