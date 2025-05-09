package com.taskflow.taskflowbackend.model.mapper.decorator;

import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.BoardStageId;
import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.mapper.TaskMapper;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import com.taskflow.taskflowbackend.model.response.BoardStageDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
abstract public class TaskMapperDecorator implements TaskMapper{
    @Autowired
    @Delegate()
    private TaskMapper taskMapper;

    @Override
    public TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = taskMapper.toDTO(task);
        if (task.getBoard() != null){
            Board board = task.getBoard();

            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(board.getId());
            boardDTO.setName(board.getName());
            boardDTO.setDescription(board.getDescription());
            boardDTO.setUsers(
                    board.getUsers().stream()
                            .map(user -> UserMeDTO.builder()
                                    .id(user.getId())
                                    .email(user.getEmail())
                                    .firstName(user.getFirstName())
                                    .lastName(user.getLastName())
                                    .build())
                            .collect(Collectors.toList())
            );
            taskDTO.setBoard(boardDTO);

        }
        if (task.getBoardStage() != null && task.getBoardStage().getId() != null) {
            BoardStageId boardStageId = task.getBoardStage().getId();
            Long stageNumber = boardStageId.getStageNumber();
            Long boardId = boardStageId.getBoardId();
            if (taskDTO.getBoardStage() == null) {
                BoardStageDTO boardStageDTO = new BoardStageDTO();
                boardStageDTO.setId(boardId);
                boardStageDTO.setStageNumber(stageNumber);
                taskDTO.setBoardStage(boardStageDTO);
            } else {
                taskDTO.getBoardStage().setId(boardId);
                taskDTO.getBoardStage().setStageNumber(stageNumber);
            }
        }
        return taskDTO;
    }

}
