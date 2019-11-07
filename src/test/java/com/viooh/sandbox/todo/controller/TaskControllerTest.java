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

    //TODO: implement Get by Id test case

    //TODO: implement Get by Id non-existing task test case

    //TODO: implement Created Close Task test case

    //TODO: implement Close Open Task test case

    //TODO: implement Close Closed Task test case

    //TODO: implement Close non-existing task test case

    private String getUri() {
        return "http://localhost:" + port + contextPath + "tasks";
    }
}
