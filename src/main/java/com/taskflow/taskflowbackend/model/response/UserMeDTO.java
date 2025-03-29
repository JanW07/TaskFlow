package com.taskflow.taskflowbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMeDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<TaskDTO> tasks;
    private List<BoardDTO> boards;
}
