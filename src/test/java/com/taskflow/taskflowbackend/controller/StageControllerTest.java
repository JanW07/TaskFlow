package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.Service.Integration;
import com.taskflow.taskflowbackend.helper.*;
import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import com.taskflow.taskflowbackend.model.response.BoardStageDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

public class StageControllerTest extends Integration implements TaskHelper, StageHelper, TaskStageHelper, LoginHelper, BoardHelper {

    private Long createBoard(String token) {
        CreateBoardDTO createBoardDTO = CreateBoardDTO.builder()
                .name("Board One")
                .description("Description One")
                .build();
        BoardDTO boardDTO = createBoard(token, createBoardDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        return boardDTO.getId();
    }

    private Long createTask(String token, Long boardId) {
        CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
                .name("Task One")
                .description("Description One")
                .build();
        TaskDTO taskDTO = createTask(token, boardId, createTaskDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);
        return taskDTO.getId();
    }


    @Test
    public void createStageHappyPath() {
        String token = getToken("admin@admin.com", "admin");

        Long boardId = createBoard(token);

        BoardStageDTO boardStageDTO = createStage(token, boardId, "Stage1")
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardStageDTO.class);

        Assertions.assertNotNull(boardStageDTO);
        Assertions.assertEquals(boardStageDTO.getName(), "Stage1");
    }

    @Test
    public void updateStageHappyPath() {
        String token = getToken("admin@admin.com", "admin");

        Long boardId = createBoard(token);

        BoardStageDTO boardStageDTO = createStage(token, boardId, "Stage1")
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardStageDTO.class);

        BoardStageDTO updateBoardStageDTO = updateStage(token, boardId, boardStageDTO.getStageNumber(), "Stage1-update")
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardStageDTO.class);

        Assertions.assertNotNull(updateBoardStageDTO);
        Assertions.assertEquals(updateBoardStageDTO.getName(), "Stage1-update");
    }

    @Test
    public void getStagesHappyPath() {
        String token = getToken("admin@admin.com", "admin");

        Long boardId = createBoard(token);

        BoardStageDTO boardStageDTO = createStage(token, boardId, "Stage1")
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardStageDTO.class);

        List<BoardStageDTO> stageDTO = getStages(token, boardId)
                .statusCode(HttpStatus.OK.value())
                .extract().as(new TypeRef<List<BoardStageDTO>>() {});


        Assertions.assertNotNull(stageDTO);
        Assertions.assertEquals(stageDTO.getLast().getName(), "Stage1");
    }

    @Test
    public void getStageByStageNumberHappyPath() {
        String token = getToken("admin@admin.com", "admin");

        Long boardId = createBoard(token);

        BoardStageDTO boardStageDTO = createStage(token, boardId, "Stage1")
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardStageDTO.class);

        BoardStageDTO stageDTO = getStageByStageNumber(token, boardId, boardStageDTO.getStageNumber())
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardStageDTO.class);


        Assertions.assertNotNull(stageDTO);
        Assertions.assertEquals(stageDTO.getName(), boardStageDTO.getName());
    }

}
