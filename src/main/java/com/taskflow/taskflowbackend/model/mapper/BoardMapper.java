package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.*;
import com.taskflow.taskflowbackend.model.mapper.decorator.BoardMapperDecorator;
import com.taskflow.taskflowbackend.model.request.*;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@DecoratedWith(BoardMapperDecorator.class)
public interface BoardMapper {

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "boardStages", ignore = true)
    BoardDTO toDTO(Board board);

    Board toEntity(CreateBoardDTO createBoardDTO);

    void updateBoardFromDTO(UpdateBoardDTO updateBoardDTO, @MappingTarget Board board);


    default Long map(BoardStageId boardStageId) {
        return boardStageId == null
                ? null
                : boardStageId.getBoardId();
    }
}
