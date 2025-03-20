package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)

public interface TaskMapper {
    @Mapping(target = "users", ignore = true)
    TaskDTO toDTO(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Task toEntity(CreateTaskDTO createTaskDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateTaskFromDTO(UpdateTaskDTO updateTaskDTO, @MappingTarget Task task);
}
