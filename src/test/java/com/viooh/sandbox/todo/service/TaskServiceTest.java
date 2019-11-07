package com.viooh.sandbox.todo.service;

import com.viooh.sandbox.todo.exception.CompletedTaskSaveException;
import com.viooh.sandbox.todo.exception.TaskAlreadyClosedException;
import com.viooh.sandbox.todo.exception.TaskNotFoundException;
import com.viooh.sandbox.todo.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Task Service Tests")
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    @DisplayName("Get Task By Title Successful")
    public void getTaskByTitle(){
        Task aTask = Task.builder().title("Go for Rugby").userId(6).completed(false).build();
        Task savedTask = taskService.createTask(aTask);

        Optional<Task> taskById = taskService.getTaskByTitle("Go for Rugby");
        assertAll (
                () -> assertTrue(taskById.isPresent(), "Task could not be found!"),
                () -> assertNotNull(taskById.get().getId()),
                () -> assertFalse(taskById.get().isCompleted(), "Task must be open!"),
                () -> assertThat(taskById.get().getTitle(), is(equalTo("Go for Rugby"))),
                () -> assertThat(taskById.get().getUserId(), is(equalTo(6)))
        );
    }

    @Test
    @DisplayName("Get Task By Id Successful")
    public void getTaskById(){
        Task completedTask = Task.builder().title("Go for Polo").userId(5).completed(false).build();
        Task savedTask = taskService.createTask(completedTask);

        Optional<Task> taskById = taskService.getTaskById(savedTask.getId());
        assertAll (
                () -> assertTrue(taskById.isPresent(), "Task could not be found!"),
                () -> assertNotNull(taskById.get().getId()),
                () -> assertFalse(taskById.get().isCompleted(), "Task must be open!"),
                () -> assertThat(taskById.get().getTitle(), is(equalTo("Go for Polo"))),
                () -> assertThat(taskById.get().getUserId(), is(equalTo(5)))
        );
    }

    @Test
    @DisplayName("Close non-existing task")
    public void testCloseNonExistingTask(){
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.closeTask(-55L);
        });
    }

    @Test
    @DisplayName("Close completed task")
    public void testCloseCompletedTask(){
        Task aTask = Task.builder().title("Go for Handball").userId(4).completed(false).build();
        Task savedTask = taskService.createTask(aTask);
        Task closedTask = taskService.closeTask(savedTask.getId());

        assertThrows(TaskAlreadyClosedException.class, () -> {
            taskService.closeTask(closedTask.getId());
        });
    }

    @Test
    @DisplayName("Close uncompleted task")
    public void testCloseTask(){
        Task aTask = Task.builder().title("Go for Basketball").userId(3).completed(false).build();
        Task savedTask = taskService.createTask(aTask);

        Task closedTask = taskService.closeTask(savedTask.getId());
        assertAll (
                () -> assertNotNull(closedTask.getId()),
                () -> assertTrue(closedTask.isCompleted(), "Task cannot be closed!"),
                () -> assertThat(closedTask.getTitle(), is(equalTo("Go for Basketball"))),
                () -> assertThat(closedTask.getUserId(), is(equalTo(3)))
        );
    }

    @Test
    @DisplayName("Create  Task")
    public void testSaveTask(){
        Task aTask = Task.builder().title("Go for Football").userId(2).completed(false).build();

        Task task = taskService.createTask(aTask);
        assertAll (
                () -> assertNotNull(task.getId()),
                () -> assertThat(task.getTitle(), is(equalTo("Go for Football"))),
                () -> assertThat(task.getUserId(), is(equalTo(2)))
        );
    }

    @Test
    @DisplayName("Create Completed Task")
    public void testCreateCompletedTask(){
        Task completedTask = Task.builder().title("Go for Tenis").userId(1).completed(true).build();

        assertThrows(CompletedTaskSaveException.class, () -> {
            taskService.createTask(completedTask);
        });
    }
}
