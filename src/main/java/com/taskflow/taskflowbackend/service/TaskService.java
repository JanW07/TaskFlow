package com.taskflow.taskflowbackend.service;

import com.taskflow.taskflowbackend.config.exception.ErrorCode;
import com.taskflow.taskflowbackend.config.exception.TaskFlowException;
import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.mapper.TaskMapper;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.repository.BoardRepository;
import com.taskflow.taskflowbackend.repository.TaskRepository;
import com.taskflow.taskflowbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public TaskDTO createTask(CreateTaskDTO createTaskDTO, Long boardId, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        Task task = new Task();
        task.setName(createTaskDTO.getName());
        task.setDescription(createTaskDTO.getDescription());
        task.setBoard(board);

        board.getTasks().add(task);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    public TaskDTO getTaskById(Long id, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.TASK_NOT_FOUND, id));

        if(!task.getBoard().getUsers().contains(user)) {
            throw new TaskFlowException(ErrorCode.TASK_NOT_FOUND, id);
        }

        return taskMapper.toDTO(task);
    }

    public List<TaskDTO> getTasksOnBoard(Long boardId, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        return board.getTasks().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO updateTask(Long id, Long boardId, String email, UpdateTaskDTO updateTaskDTO){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.TASK_NOT_FOUND, id));
        if(!board.getTasks().contains(task)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        taskMapper.updateTaskFromDTO(updateTaskDTO, task);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    public void deleteTask(Long id, Long boardId, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.TASK_NOT_FOUND, id));
        if(!board.getTasks().contains(task)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        board.getTasks().remove(task);
        boardRepository.save(board);

        taskRepository.delete(task);
    }

}
