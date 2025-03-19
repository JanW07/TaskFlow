package com.taskflow.taskflowbackend.repository;

import com.taskflow.taskflowbackend.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
