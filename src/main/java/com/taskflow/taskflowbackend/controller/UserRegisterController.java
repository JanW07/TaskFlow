package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.model.request.CreateUserDTO;
import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import com.taskflow.taskflowbackend.repository.UserRepository;
import com.taskflow.taskflowbackend.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserMeDTO> registerUser(@RequestBody CreateUserDTO createUserDTO) {
        UserMeDTO userMeDTO = userRegisterService.registerUser(createUserDTO);
        return ResponseEntity.ok(userMeDTO);
    }
}
