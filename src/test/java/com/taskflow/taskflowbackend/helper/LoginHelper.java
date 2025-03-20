package com.taskflow.taskflowbackend.helper;


import com.taskflow.taskflowbackend.model.request.AuthenticationRequest;
import com.taskflow.taskflowbackend.model.response.LoginResponseDTO;

public interface LoginHelper extends AuthorizedUserTest {
    String LOGIN_URL = "/auth/login";

    default String getToken(String login, String password) {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .login(login)
                .password(password)
                .build();
        LoginResponseDTO response = whenAnonymousWithBody(request)
                .post(LOGIN_URL)
                .then()
                .extract()
                .as(LoginResponseDTO.class);

        return response.getToken();
    }
}