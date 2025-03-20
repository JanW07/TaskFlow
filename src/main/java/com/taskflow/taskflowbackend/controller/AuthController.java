package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.config.JwtUtil;
import com.taskflow.taskflowbackend.config.UserDetailService;
import com.taskflow.taskflowbackend.model.response.LoginResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getLogin());

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(LoginResponseDTO.builder().token(token).build());
    }

    // Request DTO for login
    @Data
    public static class AuthRequest {
        private String login;
        private String password;
    }
}
