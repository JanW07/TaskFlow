package com.taskflow.taskflowbackend.model.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDTO {
    @NonNull
    private String name;

    private String description;
}
