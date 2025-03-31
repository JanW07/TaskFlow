package com.taskflow.taskflowbackend.helper;

import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import io.restassured.response.ValidatableResponse;

public interface TaskStageHelper extends AuthorizedUserTest{
    String URL = "/board/";

    default ValidatableResponse changeTaskStage(String token, Long boardId, Long taskId, Long stageNumber) {
        return whenAuthenticated(token).patch(URL + boardId + "/change-stage/" + stageNumber + "/task/" + taskId).then();
    }
}
