package com.taskflow.taskflowbackend.repository;

import com.taskflow.taskflowbackend.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUsersLogin(String login);
}
