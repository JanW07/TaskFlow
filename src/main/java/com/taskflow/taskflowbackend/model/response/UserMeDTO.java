package com.taskflow.taskflowbackend.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
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
