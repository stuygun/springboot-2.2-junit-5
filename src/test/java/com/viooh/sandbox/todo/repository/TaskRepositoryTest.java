package com.viooh.sandbox.todo.repository;

import com.viooh.sandbox.todo.model.Task;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@DisplayName("Task CRUD Tests")
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    @DisplayName("Get Task By Title Successful")
    public void getTaskByTitleTest(){
        Task savedTask = saveTask("Go for bear", 97);
        Optional<Task> task = taskRepository.findOneByTitle(savedTask.getTitle());
        assertAll (
                () -> assertNotNull(task.get().getId()),
                () -> assertThat(task.get().getTitle(), is(equalTo("Go for bear"))),
                () -> assertThat(task.get().getUserId(), is(equalTo(97)))
        );
    }

    @Test
    @DisplayName("Get Task By Id Successful")
    public void getTaskByIdTest(){
        Task savedTask = saveTask("Go for swimming", 98);
        Optional<Task> task = taskRepository.findOneById(savedTask.getId());
        assertAll (
                () -> assertNotNull(task.get().getId()),
                () -> assertThat(task.get().getTitle(), is(equalTo("Go for swimming"))),
                () -> assertThat(task.get().getUserId(), is(equalTo(98)))
        );
    }

    @Test
    @DisplayName("Save Task Successful")
    public void saveTaskTest(){
        Task savedTask = saveTask("Go for shopping", 99);
        assertAll (
                () -> assertNotNull(savedTask.getId()),
                () -> assertThat(savedTask.getTitle(), is(equalTo("Go for shopping"))),
                () -> assertThat(savedTask.getUserId(), is(equalTo(99)))
        );
    }
    

    private Task saveTask(String taskTitle, Integer userId) {
        Task goForShoppingTask = Task.builder().title(taskTitle).userId(userId).build();
        return taskRepository.save(goForShoppingTask);
    }
}
