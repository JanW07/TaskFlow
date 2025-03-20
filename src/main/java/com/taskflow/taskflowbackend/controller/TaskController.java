package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board/{boardId}/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long boardId, @RequestBody CreateTaskDTO createTaskDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        TaskDTO taskDTO = taskService.createTask(createTaskDTO, boardId, login);
        return ResponseEntity.ok(taskDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        TaskDTO taskDTO = taskService.getTaskById(id, login);
        return ResponseEntity.ok(taskDTO);
    }

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getTasksOnBoard(@PathVariable Long boardId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        List<TaskDTO> tasksDTO = taskService.getTasksOnBoard(boardId, login);
        return ResponseEntity.ok(tasksDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @PathVariable Long boardId, @RequestBody UpdateTaskDTO updateTaskDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        TaskDTO taskDTO = taskService.updateTask(id, boardId, login, updateTaskDTO);
        return ResponseEntity.ok(taskDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @PathVariable Long boardId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        taskService.deleteTask(id, boardId, login);
        return ResponseEntity.ok().build();
    }
}
