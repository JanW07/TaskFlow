package com.taskflow.taskflowbackend.service;

import com.taskflow.taskflowbackend.config.exception.ErrorCode;
import com.taskflow.taskflowbackend.config.exception.TaskFlowException;
import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.BoardStage;
import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.mapper.StageMapper;
import com.taskflow.taskflowbackend.model.mapper.TaskMapper;
import com.taskflow.taskflowbackend.model.response.BoardStageDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.repository.BoardRepository;
import com.taskflow.taskflowbackend.repository.TaskRepository;
import com.taskflow.taskflowbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStageService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final StageMapper stageMapper;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public TaskDTO changeTaskStage(Long boardId, Long taskId, Long stageNumber, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.TASK_NOT_FOUND, taskId));

        BoardStage boardStage = board.getBoardStages().stream()
                .filter(stage -> stage.getId().getStageNumber().equals(stageNumber))
                .findFirst()
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_STAGE_NOT_FOUND, stageNumber));

        task.setBoardStage(boardStage);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

}
