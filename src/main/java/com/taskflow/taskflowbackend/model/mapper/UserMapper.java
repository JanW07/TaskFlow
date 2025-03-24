package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = { BoardMapper.class, TaskMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    UserMeDTO toUserMeDTO(User user);

    //User toEntity(UserMeDTO userMeDTO); //todo create user me dto

    //void updateUserFromDTO(UserMeDTO userMeDTO, @MappingTarget User user); //todo update user me dto
}
