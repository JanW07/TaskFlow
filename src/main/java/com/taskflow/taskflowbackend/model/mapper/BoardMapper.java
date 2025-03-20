package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.*;
import com.taskflow.taskflowbackend.model.request.*;
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
    BoardDTO toDTO(Board board);

    Board toEntity(CreateBoardDTO createBoardDTO);

    void updateBoardFromDTO(UpdateBoardDTO updateBoardDTO, @MappingTarget Board board);
}
