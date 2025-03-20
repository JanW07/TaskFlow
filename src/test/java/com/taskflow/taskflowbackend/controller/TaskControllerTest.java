package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.Service.Integration;
import com.taskflow.taskflowbackend.helper.LoginHelper;
import com.taskflow.taskflowbackend.helper.TaskHelper;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import io.jsonwebtoken.lang.Assert;
import io.restassured.common.mapper.TypeRef;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

public class TaskControllerTest extends Integration implements TaskHelper, LoginHelper {
    @Test
    public void createTaskHappyPath() {
        String token = getToken("admin", "admin");
        CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
                .name("Task One")
                .description("Description One")
                .build();
        TaskDTO taskDTO = createTask(token, createTaskDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        Assertions.assertEquals(createTaskDTO.getName(), taskDTO.getName());
        Assertions.assertEquals(createTaskDTO.getDescription(), taskDTO.getDescription());
    }

    @Test
    public void getTaskByIdHappyPath() {
        String token = getToken("admin", "admin");
        CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
                .name("Task Two")
                .description("Description Two")
                .build();
        TaskDTO createdTask = createTask(token, createTaskDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        TaskDTO fetchedTask = getTaskById(token, createdTask.getId())
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        Assertions.assertEquals(createTaskDTO.getName(), fetchedTask.getName());
        Assertions.assertEquals(createTaskDTO.getDescription(), fetchedTask.getDescription());
    }

    @Test
    public void getTasksHappyPath() {
        String token = getToken("admin", "admin");

        // Create two tasks
        CreateTaskDTO createTaskDTO1 = CreateTaskDTO.builder()
                .name("Task Three")
                .description("Description Three")
                .build();
        createTask(token, createTaskDTO1)
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        CreateTaskDTO createTaskDTO2 = CreateTaskDTO.builder()
                .name("Task Four")
                .description("Description Four")
                .build();
        createTask(token, createTaskDTO2)
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        // Extract list of tasks
        List<TaskDTO> tasksDTO = getTasks(token)
                .statusCode(HttpStatus.OK.value())
                .extract().as(new TypeRef<List<TaskDTO>>() {});

        // Verify that at least 2 tasks exist in the list.
        Assertions.assertTrue(tasksDTO.size() >= 2, "Expected at least 2 tasks in the list");
    }

    @Test
    public void updateTaskHappyPath() {
        String token = getToken("admin", "admin");
        // Create a task
        CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
                .name("Task Five")
                .description("Old Description")
                .build();
        TaskDTO createdTask = createTask(token, createTaskDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        // Update the task
        UpdateTaskDTO updateTaskDTO = UpdateTaskDTO.builder()
                .name("Task Five Updated")
                .description("New Description")
                .build();
        TaskDTO updatedTask = updateTask(token, createdTask.getId(), updateTaskDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        Assertions.assertEquals("Task Five Updated", updatedTask.getName());
        Assertions.assertEquals("New Description", updatedTask.getDescription());
    }

    @Test
    public void deleteTaskHappyPath() {
        String token = getToken("admin", "admin");
        // Create a task to delete
        CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
                .name("Task Six")
                .description("To be deleted")
                .build();
        TaskDTO createdTask = createTask(token, createTaskDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        // Delete the task
        deleteTask(token, createdTask.getId())
                .statusCode(HttpStatus.OK.value());

        // Verify that fetching the deleted task returns 404 (or another error code)
        getTaskById(token, createdTask.getId())
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    // Bad Paths / Negative Tests

    @Test
    public void createTaskWithInvalidTokenShouldFail() {
        String invalidToken = "invalid-token";
        CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
                .name("Invalid Token Task")
                .description("Should fail")
                .build();
        // Expect unauthorized (or 403) due to invalid token
        createTask(invalidToken, createTaskDTO)
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getTaskByNonExistingIdShouldFail() {
        String token = getToken("admin", "admin");
        // Use an ID that likely doesn't exist (e.g., 99999)
        getTaskById(token, 99999L)
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateTaskWithOnlyDescription() {
        String token = getToken("admin", "admin");
        CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
                .name("Task Seven")
                .description("Valid Description")
                .build();
        TaskDTO createdTask = createTask(token, createTaskDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        UpdateTaskDTO updateTaskDTO = UpdateTaskDTO.builder()
                .name(null)
                .description("New Description")
                .build();
        updateTask(token, createdTask.getId(), updateTaskDTO)
                .statusCode(HttpStatus.OK.value());
    }
}
