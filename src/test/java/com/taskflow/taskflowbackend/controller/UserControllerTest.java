package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.Service.Integration;
import com.taskflow.taskflowbackend.helper.BoardHelper;
import com.taskflow.taskflowbackend.helper.LoginHelper;
import com.taskflow.taskflowbackend.helper.UserHelper;
import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

public class UserControllerTest extends Integration implements UserHelper, LoginHelper {
    @Test
    public void getUserDTOMeHappyPath() {
        String token = getToken("admin", "admin");
        UserMeDTO userMeDTO = getUserMe(token)
                .statusCode(HttpStatus.OK.value())
                .extract().as(UserMeDTO.class);

        Assertions.assertEquals(userMeDTO.getFirstName(), "first_name");
        Assertions.assertEquals(userMeDTO.getLastName(), "last_name");
    }
}
