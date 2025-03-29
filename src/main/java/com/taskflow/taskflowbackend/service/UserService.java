package com.taskflow.taskflowbackend.service;

import com.taskflow.taskflowbackend.config.exception.ErrorCode;
import com.taskflow.taskflowbackend.config.exception.TaskFlowException;
import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.mapper.UserMapper;
import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import com.taskflow.taskflowbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserMeDTO getUserMe(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));
        return userMapper.toUserMeDTO(user);
    }
}
