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
    private String email;
    @NonNull
    private String password;
}
