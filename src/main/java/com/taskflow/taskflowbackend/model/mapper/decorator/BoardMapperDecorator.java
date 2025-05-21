package com.taskflow.taskflowbackend.model.mapper.decorator;

import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.BoardStage;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.mapper.BoardMapper;
import com.taskflow.taskflowbackend.model.mapper.TaskMapper;
import com.taskflow.taskflowbackend.model.mapper.UserMapper;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import com.taskflow.taskflowbackend.model.response.BoardStageDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
abstract public class BoardMapperDecorator implements BoardMapper {
    @Autowired
    @Delegate()
    private BoardMapper boardMapper;

    @Autowired
    @Delegate()
    private TaskMapper taskMapper;

    @Override
    public BoardDTO toDTO(Board board) {
        BoardDTO dto = boardMapper.toDTO(board);

        if (board.getBoardStages() != null) {
            dto.setBoardStages(
                    board.getBoardStages().stream()
                            .map(this::mapStage)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    private BoardStageDTO mapStage(BoardStage stage) {
        List<TaskDTO> taskDTOs = (stage.getTasks() == null)
                ? Collections.emptyList()
                : stage.getTasks().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());

        return BoardStageDTO.builder()
                .id(stage.getId().getBoardId())
                .stageNumber(stage.getId().getStageNumber())
                .name(stage.getName())
                .tasks(taskDTOs)
                .build();
    }


}
