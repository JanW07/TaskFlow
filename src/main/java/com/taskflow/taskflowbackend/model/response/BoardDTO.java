package com.taskflow.taskflowbackend.model.response;

import com.taskflow.taskflowbackend.model.entity.BoardStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String name;
    private String description;
    private List<TaskDTO> tasks;
    private List<UserMeDTO> users;
    private List<BoardStageDTO> boardStages;
}
