package com.taskflow.taskflowbackend.helper;

import io.restassured.response.ValidatableResponse;

public interface StageHelper extends AuthorizedUserTest{
    String URL = "/board/";

    default ValidatableResponse createStage(String token, Long boardId, String stageName) {
        return whenAuthenticatedWithBody(stageName, token).post(URL + boardId + "/stage").then();
    }

    default ValidatableResponse updateStage(String token, Long boardId, Long stageNumber, String stageName) {
        return whenAuthenticatedWithBody(stageName, token).patch(URL + boardId + "/stage/" + stageNumber).then();
    }

    default ValidatableResponse deleteStage(String token, Long boardId, Long stageNumber) {
        return whenAuthenticated(token).delete(URL + boardId + "/stage/" + stageNumber).then();
    }

    default ValidatableResponse getStages(String token, Long boardId) {
        return whenAuthenticated(token).get(URL + boardId + "/stage").then();
    }

    default ValidatableResponse getStageByStageNumber(String token, Long boardId, Long stageNumber) {
        return whenAuthenticated(token).get(URL + boardId + "/stage/" + stageNumber).then();
    }
}
