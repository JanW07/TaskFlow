package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import com.taskflow.taskflowbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/me")
    public ResponseEntity<UserMeDTO> getUserMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        UserMeDTO userMeDTO = userService.getUserMe(login);
        return ResponseEntity.ok(userMeDTO);
    }
}
