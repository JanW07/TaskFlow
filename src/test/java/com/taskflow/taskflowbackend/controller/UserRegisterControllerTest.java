package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.Service.Integration;
import com.taskflow.taskflowbackend.helper.LoginHelper;
import com.taskflow.taskflowbackend.helper.UserHelper;
import com.taskflow.taskflowbackend.helper.UserRegisterHelper;
import com.taskflow.taskflowbackend.model.request.CreateUserDTO;
import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class UserRegisterControllerTest extends Integration implements UserRegisterHelper, LoginHelper {
    @Test
    public void registerUserHappyPath() {
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .email("test@test.pl")
                .firstName("test")
                .lastName("test")
                .password("test")
                .build();
        UserMeDTO userMeDTO = registerUser(createUserDTO)
                .statusCode(HttpStatus.OK.value()).extract().as(UserMeDTO.class);
        Assertions.assertNotNull(userMeDTO);

        String token = getToken("test@test.pl", "test");
        Assertions.assertNotNull(token);
    }
}
