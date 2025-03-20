package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.Service.Integration;
import com.taskflow.taskflowbackend.helper.LoginHelper;
import com.taskflow.taskflowbackend.helper.TaskHelper;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import io.jsonwebtoken.lang.Assert;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class TaskControllerTest extends Integration implements TaskHelper, LoginHelper {
    @Test
    public void createTaskHappyPath() {
        String token = getToken("admin", "admin");
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        createTaskDTO.setName("name");
        createTaskDTO.setDescription("description");
        TaskDTO taskDTO = createTask(token, createTaskDTO).statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        Assertions.assertEquals(taskDTO.getName(), createTaskDTO.getName());
        Assertions.assertEquals(taskDTO.getDescription(), createTaskDTO.getDescription());
    }
}
