package com.taskflow.taskflowbackend.model.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {
    @NonNull
    private String login;
    @NonNull
    private String password;
}
