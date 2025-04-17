package com.taskflow.taskflowbackend.repository;

import com.taskflow.taskflowbackend.model.entity.BoardStage;
import com.taskflow.taskflowbackend.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByBoardStage_Id_BoardIdAndBoardStage_Id_StageNumber(Long boardId, Long stageNumber);
}
