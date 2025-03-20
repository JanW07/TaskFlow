package com.taskflow.taskflowbackend.repository;

import com.taskflow.taskflowbackend.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
