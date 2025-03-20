package com.taskflow.taskflowbackend.helper;

import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import io.restassured.response.ValidatableResponse;

public interface TaskHelper extends AuthorizedUserTest{
    String URL = "/board";

    default ValidatableResponse createTask(String token, Long boardId, CreateTaskDTO createTaskDTO) {
        return whenAuthenticatedWithBody(createTaskDTO, token).post(URL + "/" + boardId + "/task").then();
    }

    default ValidatableResponse getTaskById(String token, Long boardId, Long id) {
        return whenAuthenticated(token).get(URL + "/" + boardId + "/task/" + id).then();
    }

    default ValidatableResponse getTasksOnBoard(String token, Long boardId) {
        return whenAuthenticated(token).get(URL + "/" + boardId + "/task").then();
    }

    default ValidatableResponse updateTask(String token, Long boardId, Long id, UpdateTaskDTO updateTaskDTO) {
        return whenAuthenticatedWithBody(updateTaskDTO, token).patch(URL + "/" + boardId + "/task/" + id).then();
    }

    default ValidatableResponse deleteTask(String token, Long boardId, Long id) {
        return whenAuthenticated(token).delete(URL + "/" + boardId + "/task/" + id).then();
    }
}
