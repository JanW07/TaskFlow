package com.taskflow.taskflowbackend.service;

import com.taskflow.taskflowbackend.config.exception.ErrorCode;
import com.taskflow.taskflowbackend.config.exception.TaskFlowException;
import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.BoardStage;
import com.taskflow.taskflowbackend.model.entity.BoardStageId;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.mapper.StageMapper;
import com.taskflow.taskflowbackend.model.mapper.TaskMapper;
import com.taskflow.taskflowbackend.model.response.BoardStageDTO;
import com.taskflow.taskflowbackend.repository.BoardRepository;
import com.taskflow.taskflowbackend.repository.StageRepository;
import com.taskflow.taskflowbackend.repository.TaskRepository;
import com.taskflow.taskflowbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final StageMapper stageMapper;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final StageRepository stageRepository;

    public BoardStageDTO createStage(Long boardId, String stageName, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        Long nextStageNumber = board.getBoardStages().stream()
                .map(stage -> stage.getId().getStageNumber())
                .max(Long::compare)
                .orElse(0L) + 1;

        BoardStage boardStage = new BoardStage();
        boardStage.setName(stageName);
        boardStage.setBoard(board);
        boardStage.setTasks(new ArrayList<>());
        boardStage.setId(new BoardStageId(board.getId(), nextStageNumber));

        board.getBoardStages().add(boardStage);
        boardRepository.save(board);

        BoardStage savedStage = stageRepository.save(boardStage);
        return stageMapper.toDTO(savedStage);
    }

    public BoardStageDTO updateStage(Long boardId, Long StageNumber, String stageName, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        BoardStage boardStage = stageRepository.findByIdStageNumber(StageNumber)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_STAGE_NOT_FOUND));
        if(!board.getBoardStages().contains(boardStage)){
            throw new TaskFlowException(ErrorCode.BOARD_STAGE_NOT_FOUND, boardId);
        }

        boardStage.setName(stageName);
        boardStage.setBoard(board);

        BoardStage savedStage = stageRepository.save(boardStage);
        return stageMapper.toDTO(savedStage);
    }

    public void deleteStage(Long boardId, Long StageNumber, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        BoardStage boardStage = stageRepository.findByIdStageNumber(StageNumber)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_STAGE_NOT_FOUND));
        if(!board.getBoardStages().contains(boardStage)){
            throw new TaskFlowException(ErrorCode.BOARD_STAGE_NOT_FOUND, boardId);
        }

        board.getBoardStages().remove(boardStage);
        boardRepository.save(board);
        stageRepository.delete(boardStage);
    }

    public List<BoardStageDTO> getStages(Long boardId, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        List<BoardStage> stages = board.getBoardStages();
        return stageMapper.toDTOList(stages);
    }

    public BoardStageDTO getStageByNumber(Long boardId, Long stageNumber, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));

        if(!board.getUsers().contains(user)){
            throw new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId);
        }

        BoardStage boardStage = board.getBoardStages().stream()
                .filter(stage -> stage.getId().getStageNumber().equals(stageNumber))
                .findFirst()
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_STAGE_NOT_FOUND, stageNumber));

        return stageMapper.toDTO(boardStage);
    }




}
