package com.taskflow.taskflowbackend.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardStageDTO {
    private Long id;
    private String name;
    private BoardDTO board;
    private List<TaskDTO> tasks;
}
