package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.BoardStage;
import com.taskflow.taskflowbackend.model.response.BoardStageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StageMapper {
    @Mapping(target = "board", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(source = "id.stageNumber", target = "stageNumber")
    BoardStageDTO toDTO(BoardStage boardStage);

    List<BoardStageDTO> toDTOList(List<BoardStage> boardStage);

    default Long map(com.taskflow.taskflowbackend.model.entity.BoardStageId boardStageId) {
        return boardStageId != null ? boardStageId.getStageNumber().longValue() : null;
    }
}
