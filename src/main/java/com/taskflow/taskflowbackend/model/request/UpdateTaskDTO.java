package com.taskflow.taskflowbackend.model.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDTO {
    private String name;
    private String description;
}
