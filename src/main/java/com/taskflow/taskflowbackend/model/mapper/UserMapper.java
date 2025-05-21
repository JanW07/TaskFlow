package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.mapper.decorator.UserMapperDecorator;
import com.taskflow.taskflowbackend.model.response.UserMeDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = { BoardMapper.class, TaskMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {
    @Mapping(target = "boards", ignore = true)
    UserMeDTO toUserMeDTO(User user);

    //User toEntity(UserMeDTO userMeDTO); //todo create user me dto

    //void updateUserFromDTO(UserMeDTO userMeDTO, @MappingTarget User user); //todo update user me dto
}
