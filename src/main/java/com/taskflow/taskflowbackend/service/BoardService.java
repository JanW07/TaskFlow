package com.taskflow.taskflowbackend.service;

import com.taskflow.taskflowbackend.config.exception.ErrorCode;
import com.taskflow.taskflowbackend.config.exception.TaskFlowException;
import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.mapper.BoardMapper;
import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.request.UpdateBoardDTO;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import com.taskflow.taskflowbackend.repository.BoardRepository;
import com.taskflow.taskflowbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    private final UserRepository userRepository;
    private final UserBoardService userBoardService;

    public BoardDTO createBoard(CreateBoardDTO createBoardDTO, String login) {
        Board board = boardMapper.toEntity(createBoardDTO);

        board.setUsers(new java.util.ArrayList<>());
        board.setTasks(new java.util.ArrayList<>());

        Board savedBoard = boardRepository.save(board);
        userBoardService.assignUser(savedBoard.getId(), login);

        Board updatedBoard = boardRepository.save(board);
        return boardMapper.toDTO(updatedBoard);
    }

    public BoardDTO getBoardById(Long id, String email) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, id));
        boolean allowed = board.getUsers().stream().anyMatch(u -> u.getEmail().equals(email));
        if (!allowed) {
            throw new TaskFlowException(ErrorCode.USER_NOT_FOUND, id);
        }
        return boardMapper.toDTO(board);
    }

    public List<BoardDTO> getAllBoards(String email) {
        List<Board> boards = boardRepository.findByUsersEmail(email);
        return boards.stream()
                .map(boardMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BoardDTO updateBoard(Long id, UpdateBoardDTO updateBoardDTO, String email) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, id));
        boolean allowed = board.getUsers().stream().anyMatch(u -> u.getEmail().equals(email));
        if (!allowed) {
            throw new TaskFlowException(ErrorCode.USER_NOT_FOUND, id);
        }
        boardMapper.updateBoardFromDTO(updateBoardDTO, board);
        Board updatedBoard = boardRepository.save(board);
        return boardMapper.toDTO(updatedBoard);
    }

    public void deleteBoard(Long id, String login) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, id));
        boolean allowed = board.getUsers().stream().anyMatch(u -> u.getEmail().equals(login));
        if (!allowed) {
            throw new TaskFlowException(ErrorCode.USER_NOT_FOUND, id);
        }
        boardRepository.deleteById(id);
    }
}
