package com.taskflow.taskflowbackend.model.request;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDTO {
    @NotNull
    private String name;

    private String description;
}
