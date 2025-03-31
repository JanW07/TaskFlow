package com.taskflow.taskflowbackend.repository;

import com.taskflow.taskflowbackend.model.entity.BoardStage;
import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StageRepository extends JpaRepository<BoardStage, Long> {
    Optional<BoardStage> findByIdStageNumber(Long stageNumber);
}
