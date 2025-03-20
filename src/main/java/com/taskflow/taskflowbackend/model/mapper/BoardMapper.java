package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.request.UpdateBoardDTO;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BoardMapper {

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "users", ignore = true)
    BoardDTO toDTO(Board board);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "users", ignore = true)
    Board toEntity(CreateBoardDTO createBoardDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateBoardFromDTO(UpdateBoardDTO updateBoardDTO, @MappingTarget Board board);
}
