package com.taskflow.taskflowbackend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardStageId implements Serializable {
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "stage_number")
    private Long stageNumber;
}
