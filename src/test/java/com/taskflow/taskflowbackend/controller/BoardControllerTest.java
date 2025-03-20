package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.Service.Integration;
import com.taskflow.taskflowbackend.helper.BoardHelper;
import com.taskflow.taskflowbackend.helper.LoginHelper;
import com.taskflow.taskflowbackend.helper.TaskHelper;
import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BoardControllerTest extends Integration implements BoardHelper, LoginHelper {
    @Test
    public void createTaskHappyPath() {
        String token = getToken("admin", "admin");
        CreateBoardDTO createBoardDTO = CreateBoardDTO.builder()
                .name("Task One")
                .description("Description One")
                .build();
        BoardDTO boardDTO = createBoard(token, createBoardDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        Assertions.assertEquals(createBoardDTO.getName(), boardDTO.getName());
        Assertions.assertEquals(createBoardDTO.getDescription(), boardDTO.getDescription());
    }
    @Test
    public void getAllBoardsHappyPath() {
        String token = getToken("admin", "admin");

        CreateBoardDTO board1 = CreateBoardDTO.builder()
                .name("Board A")
                .description("Description A")
                .build();
        createBoard(token, board1)
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        CreateBoardDTO board2 = CreateBoardDTO.builder()
                .name("Board B")
                .description("Description B")
                .build();
        createBoard(token, board2)
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        List<BoardDTO> boards = getAllBoards(token)
                .statusCode(HttpStatus.OK.value())
                .extract().as(new TypeRef<List<BoardDTO>>() {});

        Assertions.assertTrue(boards.size() >= 2, "Expected at least 2 boards");
    }

    @Test
    public void getBoardByIdHappyPath() {
        String token = getToken("admin", "admin");

        CreateBoardDTO createBoardDTO = CreateBoardDTO.builder()
                .name("Board To Retrieve")
                .description("Description Retrieve")
                .build();
        BoardDTO createdBoard = createBoard(token, createBoardDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        BoardDTO fetchedBoard = getBoardById(token, createdBoard.getId())
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        Assertions.assertEquals(createdBoard.getId(), fetchedBoard.getId());
        Assertions.assertEquals("Board To Retrieve", fetchedBoard.getName());
    }

    @Test
    public void updateBoardHappyPath() {
        String token = getToken("admin", "admin");

        CreateBoardDTO createBoardDTO = CreateBoardDTO.builder()
                .name("Board To Update")
                .description("Old Description")
                .build();
        BoardDTO createdBoard = createBoard(token, createBoardDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        com.taskflow.taskflowbackend.model.request.UpdateBoardDTO updateBoardDTO = com.taskflow.taskflowbackend.model.request.UpdateBoardDTO.builder()
                .name("Updated Board")
                .description("Updated Description")
                .build();
        BoardDTO updatedBoard = updateBoard(token, createdBoard.getId(), updateBoardDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        Assertions.assertEquals("Updated Board", updatedBoard.getName());
        Assertions.assertEquals("Updated Description", updatedBoard.getDescription());
    }

    @Test
    public void deleteBoardHappyPath() {
        String token = getToken("admin", "admin");

        CreateBoardDTO createBoardDTO = CreateBoardDTO.builder()
                .name("Board To Delete")
                .description("To be deleted")
                .build();
        BoardDTO createdBoard = createBoard(token, createBoardDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        deleteBoard(token, createdBoard.getId())
                .statusCode(HttpStatus.OK.value());

        getBoardById(token, createdBoard.getId())
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
