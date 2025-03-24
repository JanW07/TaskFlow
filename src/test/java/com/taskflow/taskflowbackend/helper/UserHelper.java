package com.taskflow.taskflowbackend.helper;

import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.request.UpdateBoardDTO;
import io.restassured.response.ValidatableResponse;

public interface UserHelper extends AuthorizedUserTest{
    String URL = "/user/me";

    default ValidatableResponse getUserMe(String token) {
        return whenAuthenticated(token).get(URL).then();
    }
}
