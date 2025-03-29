package com.taskflow.taskflowbackend.helper;

import com.taskflow.taskflowbackend.model.request.CreateUserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public interface UserRegisterHelper extends AuthorizedUserTest{
    String URL = "/auth/register";

    default ValidatableResponse registerUser(CreateUserDTO createUserDTO) {
        return given()
                .contentType(ContentType.JSON)
                .body(createUserDTO)
                .post(URL)
                .then();
    }
}
