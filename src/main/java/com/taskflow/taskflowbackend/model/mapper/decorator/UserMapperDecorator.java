package com.taskflow.taskflowbackend.model.mapper.decorator;

import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.BoardStageId;
import com.taskflow.taskflowbackend.model.entity.Task;
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

import java.util.List;
import java.util.stream.Collectors;

@Component
abstract public class UserMapperDecorator implements UserMapper {
    @Autowired
    @Delegate()
    private UserMapper userMapper;

    @Autowired
    @Delegate()
    private BoardMapper boardMapper;

    @Override
    public UserMeDTO toUserMeDTO(User user) {
        UserMeDTO dto = userMapper.toUserMeDTO(user);

        if (!user.getBoards().isEmpty()) {
            List<BoardDTO> boardDTOs = user.getBoards().stream()
                    .map(boardMapper::toDTO)
                    .peek(b -> b.setBoardStages(null))
                    .collect(Collectors.toList());

            dto.setBoards(boardDTOs);
        }

        return dto;
    }


}
