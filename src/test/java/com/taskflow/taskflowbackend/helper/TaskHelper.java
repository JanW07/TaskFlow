package com.taskflow.taskflowbackend.helper;

import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import io.restassured.response.ValidatableResponse;

public interface TaskHelper extends AuthorizedUserTest{
    String URL = "/tasks";

    default ValidatableResponse createTask(String token, CreateTaskDTO createTaskDTO) {
        return whenAuthenticatedWithBody(createTaskDTO, token).post(URL).then();
    }

    default ValidatableResponse getTaskById(String token, Long id) {
        return whenAuthenticated(token).get(URL + "/" + id).then();
    }

    default ValidatableResponse getTasks(String token) {
        return whenAuthenticated(token).get(URL).then();
    }

    default ValidatableResponse updateTask(String token, Long id, UpdateTaskDTO updateTaskDTO) {
        return whenAuthenticatedWithBody(updateTaskDTO, token).patch(URL + "/" + id).then();
    }

    default ValidatableResponse deleteTask(String token, Long id) {
        return whenAuthenticated(token).delete(URL + "/" + id).then();
    }
}
