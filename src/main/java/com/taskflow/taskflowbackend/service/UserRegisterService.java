package com.taskflow.taskflowbackend.service;

import com.taskflow.taskflowbackend.config.exception.ErrorCode;
import com.taskflow.taskflowbackend.config.exception.TaskFlowException;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.mapper.UserMapper;
import com.taskflow.taskflowbackend.model.request.CreateUserDTO;
import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import com.taskflow.taskflowbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserMeDTO registerUser(CreateUserDTO createUserDTO) {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new TaskFlowException(ErrorCode.USER_NOT_FOUND, createUserDTO.getEmail());
        }

        User user = User.builder()
                .email(createUserDTO.getEmail())
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        return userMapper.toUserMeDTO(savedUser);
    }
}
