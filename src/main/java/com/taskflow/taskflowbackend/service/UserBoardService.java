package com.taskflow.taskflowbackend.service;

import com.taskflow.taskflowbackend.config.exception.ErrorCode;
import com.taskflow.taskflowbackend.config.exception.TaskFlowException;
import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.repository.BoardRepository;
import com.taskflow.taskflowbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void assignUser(Long boardId, String email) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.BOARD_NOT_FOUND, boardId));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TaskFlowException(ErrorCode.USER_NOT_FOUND, email));

        if (!board.getUsers().contains(user)) {
            board.getUsers().add(user);
        }
        if (user.getBoards() == null) {
            user.setBoards(new java.util.ArrayList<>());
        }
        if (!user.getBoards().contains(board)) {
            user.getBoards().add(board);
        }
        boardRepository.save(board);
    }
}
