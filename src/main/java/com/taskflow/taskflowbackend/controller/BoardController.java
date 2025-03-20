package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.request.UpdateBoardDTO;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import com.taskflow.taskflowbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody CreateBoardDTO createBoardDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        BoardDTO boardDTO = boardService.createBoard(createBoardDTO, login);
        return ResponseEntity.ok(boardDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        BoardDTO boardDTO = boardService.getBoardById(id, login);
        return ResponseEntity.ok(boardDTO);
    }

    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        List<BoardDTO> boards = boardService.getAllBoards(login);
        return ResponseEntity.ok(boards);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long id, @RequestBody UpdateBoardDTO updateBoardDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        BoardDTO boardDTO = boardService.updateBoard(id, updateBoardDTO, login);
        return ResponseEntity.ok(boardDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        boardService.deleteBoard(id, login);
        return ResponseEntity.ok().build();
    }
}
