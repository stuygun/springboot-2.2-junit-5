package com.viooh.sandbox.todo.controller;

import com.viooh.sandbox.todo.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Task Controller Integration Tests")
public class TaskControllerTest {

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Create task successful")
    public void createTask(){
        Task task_from_controller = Task.builder().userId(111).title("Task from controller").build();
        ResponseEntity<Task> taskResponseEntity = this.restTemplate.postForEntity(getUri(), task_from_controller, Task.class);
        assertAll (
                () -> assertNotNull(taskResponseEntity),
                () -> assertThat(taskResponseEntity.getStatusCode(), is(equalTo(HttpStatus.OK))),
                () -> assertNotNull(taskResponseEntity.getBody().getId()),
                () -> assertThat(taskResponseEntity.getBody().getTitle(), is(equalTo("Task from controller"))),
                () -> assertThat(taskResponseEntity.getBody().getUserId(), is(equalTo(111)))
        );
    }

    @Test
    @DisplayName("Get By Id successful")
    public void getById(){
        Task task_from_controller = Task.builder().userId(222).title("Task from controller").build();
        ResponseEntity<Task> taskResponseEntity = this.restTemplate.postForEntity(getUri(), task_from_controller, Task.class);
        assertAll (
                () -> assertNotNull(taskResponseEntity),
                () -> assertThat(taskResponseEntity.getStatusCode(), is(equalTo(HttpStatus.OK))),
                () -> assertNotNull(taskResponseEntity.getBody().getId()),
                () -> assertThat(taskResponseEntity.getBody().getTitle(), is(equalTo("Task from controller"))),
                () -> assertThat(taskResponseEntity.getBody().getUserId(), is(equalTo(222)))
        );

        ResponseEntity<Task> getByIdTask = this.restTemplate.getForEntity(getUri() + "/" + taskResponseEntity.getBody().getId(), Task.class);
        assertAll (
                () -> assertNotNull(getByIdTask),
                () -> assertThat(getByIdTask.getStatusCode(), is(equalTo(HttpStatus.OK))),
                () -> assertNotNull(getByIdTask.getBody().getId()),
                () -> assertThat(getByIdTask.getBody().getTitle(), is(equalTo("Task from controller"))),
                () -> assertThat(getByIdTask.getBody().getUserId(), is(equalTo(222)))
        );
    }

    @Test
    @DisplayName("Get By Id non-existing task")
    public void getByIdNonExistingTask(){
        ResponseEntity<String> errorResponse = this.restTemplate.getForEntity(getUri() + "/9999", String.class);
        assertAll (
                () -> assertNotNull(errorResponse),
                () -> assertThat(errorResponse.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)))
        );
    }

    @DisplayName("Create Close Task")
    @Test
    public void createCloseTask(){
        Task task_from_controller = Task.builder().userId(333).title("Task from controller").completed(true).build();
        ResponseEntity<String> errorResponse = this.restTemplate.postForEntity(getUri(), task_from_controller, String.class);
        assertAll (
                () -> assertNotNull(errorResponse),
                () -> assertThat(errorResponse.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR))),
                () -> assertThat(errorResponse.getBody(), is(equalTo("An error occurred!")))
        );
    }

    @Test
    @DisplayName("Close Open Task successful")
    public void closeOpenTask(){
        Task task_from_controller = Task.builder().userId(444).title("Task from controller").build();
        ResponseEntity<Task> taskResponseEntity = this.restTemplate.postForEntity(getUri(), task_from_controller, Task.class);
        assertAll (
                () -> assertNotNull(taskResponseEntity),
                () -> assertThat(taskResponseEntity.getStatusCode(), is(equalTo(HttpStatus.OK))),
                () -> assertNotNull(taskResponseEntity.getBody().getId()),
                () -> assertThat(taskResponseEntity.getBody().isCompleted(), is(equalTo(false))),
                () -> assertThat(taskResponseEntity.getBody().getTitle(), is(equalTo("Task from controller"))),
                () -> assertThat(taskResponseEntity.getBody().getUserId(), is(equalTo(444)))
        );

        ResponseEntity<Task> getByIdTask = this.restTemplate.getForEntity(getUri() + "/" + taskResponseEntity.getBody().getId() + "/close", Task.class);
        assertAll (
                () -> assertNotNull(getByIdTask),
                () -> assertThat(getByIdTask.getStatusCode(), is(equalTo(HttpStatus.OK))),
                () -> assertThat(getByIdTask.getBody().isCompleted(), is(equalTo(true))),
                () -> assertNotNull(getByIdTask.getBody().getId()),
                () -> assertThat(getByIdTask.getBody().getTitle(), is(equalTo("Task from controller"))),
                () -> assertThat(getByIdTask.getBody().getUserId(), is(equalTo(444)))
        );
    }

    @Test
    @DisplayName("Close Closed Task successful")
    public void closeClosedTask(){
        Task task_from_controller = Task.builder().userId(555).title("Task from controller").build();
        ResponseEntity<Task> taskResponseEntity = this.restTemplate.postForEntity(getUri(), task_from_controller, Task.class);
        assertAll (
                () -> assertNotNull(taskResponseEntity),
                () -> assertThat(taskResponseEntity.getStatusCode(), is(equalTo(HttpStatus.OK))),
                () -> assertNotNull(taskResponseEntity.getBody().getId()),
                () -> assertThat(taskResponseEntity.getBody().isCompleted(), is(equalTo(false))),
                () -> assertThat(taskResponseEntity.getBody().getTitle(), is(equalTo("Task from controller"))),
                () -> assertThat(taskResponseEntity.getBody().getUserId(), is(equalTo(555)))
        );

        ResponseEntity<Task> getByIdTask = this.restTemplate.getForEntity(getUri() + "/" + taskResponseEntity.getBody().getId() + "/close", Task.class);
        assertAll (
                () -> assertNotNull(getByIdTask),
                () -> assertThat(getByIdTask.getStatusCode(), is(equalTo(HttpStatus.OK))),
                () -> assertThat(getByIdTask.getBody().isCompleted(), is(equalTo(true))),
                () -> assertNotNull(getByIdTask.getBody().getId()),
                () -> assertThat(getByIdTask.getBody().getTitle(), is(equalTo("Task from controller"))),
                () -> assertThat(getByIdTask.getBody().getUserId(), is(equalTo(555)))
        );

        ResponseEntity<String> errorResponse = this.restTemplate.getForEntity(getUri() + "/" + taskResponseEntity.getBody().getId() + "/close", String.class);
        assertAll (
                () -> assertNotNull(errorResponse),
                () -> assertThat(errorResponse.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR))),
                () -> assertThat(errorResponse.getBody(), is(equalTo("An error occurred!")))
        );
    }

    @Test
    @DisplayName("Get By Id non-existing task")
    public void closeNonExistingTask(){
        ResponseEntity<String> errorResponse = this.restTemplate.getForEntity(getUri() + "/9999/close", String.class);
        assertAll (
                () -> assertNotNull(errorResponse),
                () -> assertThat(errorResponse.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR))),
                () -> assertThat(errorResponse.getBody(), is(equalTo("An error occurred!")))
        );
    }

    private String getUri() {
        return "http://localhost:" + port + contextPath + "tasks";
    }
}

