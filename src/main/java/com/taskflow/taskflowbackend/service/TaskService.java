package com.taskflow.taskflowbackend.service;

import com.taskflow.taskflowbackend.config.exception.ErrorCode;
import com.taskflow.taskflowbackend.config.exception.TaskFlowException;
import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.mapper.TaskMapper;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDTO createTask(CreateTaskDTO createTaskDTO){
        Task task = new Task();
        task.setName(createTaskDTO.getName());
        task.setDescription(createTaskDTO.getDescription());

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    public TaskDTO getTask(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.TASK_NOT_FOUND, id));
        return taskMapper.toDTO(task);
    }

    public List<TaskDTO> getTasks(){
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO updateTask(Long id, UpdateTaskDTO updateTaskDTO){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.TASK_NOT_FOUND, id));
        taskMapper.updateTaskFromDTO(updateTaskDTO, task);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.TASK_NOT_FOUND, id));
        taskRepository.delete(task);
    }

}
