package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.mapper.decorator.TaskMapperDecorator;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@DecoratedWith(TaskMapperDecorator.class)
public interface TaskMapper {
    @Mapping(target = "board", ignore = true)
    @Mapping(target = "boardStage", ignore = true)
    TaskDTO toDTO(Task task);

    Task toEntity(CreateTaskDTO createTaskDTO);

    void updateTaskFromDTO(UpdateTaskDTO updateTaskDTO, @MappingTarget Task task);

    default Long map(com.taskflow.taskflowbackend.model.entity.BoardStageId boardStageId) {
        return boardStageId != null ? boardStageId.getStageNumber().longValue() : null;
    }
}
