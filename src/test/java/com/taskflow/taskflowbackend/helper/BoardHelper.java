package com.taskflow.taskflowbackend.helper;

import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateBoardDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import io.restassured.response.ValidatableResponse;

public interface BoardHelper extends AuthorizedUserTest{
    String URL = "/board";

    default ValidatableResponse createBoard(String token, CreateBoardDTO createBoardDTO) {
        return whenAuthenticatedWithBody(createBoardDTO, token).post(URL).then();
    }

    default ValidatableResponse getBoardById(String token, Long id) {
        return whenAuthenticated(token).get(URL + "/" + id).then();
    }

    default ValidatableResponse getAllBoards(String token) {
        return whenAuthenticated(token).get(URL).then();
    }

    default ValidatableResponse updateBoard(String token, Long id, UpdateBoardDTO updateBoardDTO) {
        return whenAuthenticatedWithBody(updateBoardDTO, token).patch(URL + "/" + id).then();
    }

    default ValidatableResponse deleteBoard(String token, Long id) {
        return whenAuthenticated(token).delete(URL + "/" + id).then();
    }
}
