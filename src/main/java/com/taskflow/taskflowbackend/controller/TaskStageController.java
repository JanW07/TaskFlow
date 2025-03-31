package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.BoardStageDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.service.TaskService;
import com.taskflow.taskflowbackend.service.TaskStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board/{boardId}/change-stage")
@RequiredArgsConstructor
public class TaskStageController {
    private final TaskStageService taskStageService;

    @PatchMapping("/{stageNumber}/task/{taskId}")
    public ResponseEntity<TaskDTO> changeTaskStage(@PathVariable Long boardId, @PathVariable Long taskId, @PathVariable Long stageNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        TaskDTO taskDTO = taskStageService.changeTaskStage(boardId, taskId, stageNumber, email);
        return ResponseEntity.ok(taskDTO);
    }
}
