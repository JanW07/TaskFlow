package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.model.response.BoardStageDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.service.StageService;
import com.taskflow.taskflowbackend.service.TaskStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board/{boardId}/stage")
@RequiredArgsConstructor
public class StageController {
    private final StageService stageService;

    @PostMapping()
    public ResponseEntity<BoardStageDTO> createStage(@PathVariable Long boardId, @RequestBody String stageName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        BoardStageDTO boardStageDTO = stageService.createStage(boardId, stageName, email);
        return ResponseEntity.ok(boardStageDTO);
    }

    @PatchMapping("/{stageNumber}")
    public ResponseEntity<BoardStageDTO> updateStage(@PathVariable Long boardId, @PathVariable Long stageNumber ,@RequestBody String stageName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        BoardStageDTO boardStageDTO = stageService.updateStage(boardId, stageNumber, stageName, email);
        return ResponseEntity.ok(boardStageDTO);
    }

    @DeleteMapping("/{stageNumber}")
    public ResponseEntity<BoardStageDTO> updateStage(@PathVariable Long boardId, @PathVariable Long stageNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        stageService.deleteStage(boardId, stageNumber, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<BoardStageDTO>> getStages(@PathVariable Long boardId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<BoardStageDTO> stages = stageService.getStages(boardId, email);
        return ResponseEntity.ok(stages);
    }

    @GetMapping("/{stageNumber}")
    public ResponseEntity<BoardStageDTO> getStageByStageNumber(@PathVariable Long boardId, @PathVariable Long stageNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        BoardStageDTO boardStageDTO = stageService.getStageByNumber(boardId, stageNumber, email);
        return ResponseEntity.ok(boardStageDTO);
    }
}
