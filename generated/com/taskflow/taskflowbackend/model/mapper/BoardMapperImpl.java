package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.Board;
import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.request.CreateBoardDTO;
import com.taskflow.taskflowbackend.model.request.UpdateBoardDTO;
import com.taskflow.taskflowbackend.model.response.BoardDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.model.response.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-20T16:04:20+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public BoardDTO toDTO(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDTO.BoardDTOBuilder boardDTO = BoardDTO.builder();

        boardDTO.id( board.getId() );
        boardDTO.name( board.getName() );
        boardDTO.description( board.getDescription() );
        boardDTO.users( userListToUserDTOList( board.getUsers() ) );

        return boardDTO.build();
    }

    @Override
    public Board toEntity(CreateBoardDTO createBoardDTO) {
        if ( createBoardDTO == null ) {
            return null;
        }

        Board.BoardBuilder<?, ?> board = Board.builder();

        board.name( createBoardDTO.getName() );
        board.description( createBoardDTO.getDescription() );

        return board.build();
    }

    @Override
    public void updateBoardFromDTO(UpdateBoardDTO updateBoardDTO, Board board) {
        if ( updateBoardDTO == null ) {
            return;
        }

        if ( updateBoardDTO.getName() != null ) {
            board.setName( updateBoardDTO.getName() );
        }
        if ( updateBoardDTO.getDescription() != null ) {
            board.setDescription( updateBoardDTO.getDescription() );
        }
    }

    protected TaskDTO taskToTaskDTO(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId( task.getId() );
        taskDTO.setName( task.getName() );
        taskDTO.setDescription( task.getDescription() );
        taskDTO.setUsers( userListToUserDTOList( task.getUsers() ) );
        taskDTO.setBoard( toDTO( task.getBoard() ) );

        return taskDTO;
    }

    protected List<TaskDTO> taskListToTaskDTOList(List<Task> list) {
        if ( list == null ) {
            return null;
        }

        List<TaskDTO> list1 = new ArrayList<TaskDTO>( list.size() );
        for ( Task task : list ) {
            list1.add( taskToTaskDTO( task ) );
        }

        return list1;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setLogin( user.getLogin() );
        userDTO.setTasks( taskListToTaskDTOList( user.getTasks() ) );

        return userDTO;
    }

    protected List<UserDTO> userListToUserDTOList(List<User> list) {
        if ( list == null ) {
            return null;
        }

        List<UserDTO> list1 = new ArrayList<UserDTO>( list.size() );
        for ( User user : list ) {
            list1.add( userToUserDTO( user ) );
        }

        return list1;
    }
}
