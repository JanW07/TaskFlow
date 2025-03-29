package com.taskflow.taskflowbackend.model.request;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
