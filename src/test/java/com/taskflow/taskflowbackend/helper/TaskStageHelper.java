package com.taskflow.taskflowbackend.helper;

import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import io.restassured.response.ValidatableResponse;

public interface TaskStageHelper extends AuthorizedUserTest{
    String URL = "/board/";

    default ValidatableResponse changeTaskStage(String token, Long boardId, Long taskId, Long stageNumber) {
        return whenAuthenticated(token).patch(URL + boardId + "/task-stage/" + stageNumber + "/task/" + taskId).then();
    }

    default ValidatableResponse getTasksOnStage(String token, Long boardId, Long stageNumber) {
        return whenAuthenticated(token).get(URL + boardId + "/task-stage/" + stageNumber).then();
    }
}
