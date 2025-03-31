package com.taskflow.taskflowbackend.controller;

import com.taskflow.taskflowbackend.Service.Integration;
import com.taskflow.taskflowbackend.helper.*;
import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import com.taskflow.taskflowbackend.model.response.BoardStageDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

public class TaskStageControllerTest extends Integration implements TaskHelper, StageHelper, TaskStageHelper, LoginHelper, BoardHelper {

    private Long createBoard(String token){
        CreateBoardDTO createBoardDTO = CreateBoardDTO.builder()
                .name("Board One")
                .description("Description One")
                .build();
        BoardDTO boardDTO = createBoard(token, createBoardDTO)
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardDTO.class);

        return boardDTO.getId();
    }
    private Long createTask(String token, Long boardId){
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
    public void createTaskHappyPath() {
        String token = getToken("admin@admin.com", "admin");

        Long boardId = createBoard(token);
        Long taskId = createTask(token, boardId);

        BoardStageDTO boardStageDTO = createStage(token, boardId, "Stage1")
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardStageDTO.class);

        TaskDTO taskDTO = changeTaskStage(token, boardId, taskId, boardStageDTO.getStageNumber())
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);
        Assertions.assertEquals(taskDTO.getBoardStage().getStageNumber(), boardStageDTO.getStageNumber());
    }

    @Test
    public void getTasksOnStageHappyPath() {
        String token = getToken("admin@admin.com", "admin");

        Long boardId = createBoard(token);
        Long taskId1 = createTask(token, boardId);
        Long taskId2 = createTask(token, boardId);

        BoardStageDTO boardStageDTO = createStage(token, boardId, "Stage1")
                .statusCode(HttpStatus.OK.value())
                .extract().as(BoardStageDTO.class);

        changeTaskStage(token, boardId, taskId1, boardStageDTO.getStageNumber())
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);
        changeTaskStage(token, boardId, taskId2, boardStageDTO.getStageNumber())
                .statusCode(HttpStatus.OK.value())
                .extract().as(TaskDTO.class);

        List<TaskDTO> taskDTOS = getTasksOnStage(token, boardId, boardStageDTO.getStageNumber())
                .statusCode(HttpStatus.OK.value())
                .extract().as(new TypeRef<List<TaskDTO>>() {
                });


        Assertions.assertNotNull(taskDTOS);
        Assertions.assertTrue(taskDTOS.size()>=2);
    }


}
