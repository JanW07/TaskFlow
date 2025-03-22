package com.taskflow.taskflowbackend.model.mapper;

import com.taskflow.taskflowbackend.model.entity.Task;
import com.taskflow.taskflowbackend.model.entity.User;
import com.taskflow.taskflowbackend.model.request.CreateTaskDTO;
import com.taskflow.taskflowbackend.model.request.UpdateTaskDTO;
import com.taskflow.taskflowbackend.model.response.TaskDTO;
import com.taskflow.taskflowbackend.model.response.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-20T16:04:07+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDTO toDTO(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId( task.getId() );
        taskDTO.setName( task.getName() );
        taskDTO.setDescription( task.getDescription() );
        taskDTO.setUsers( userListToUserDTOList( task.getUsers() ) );

        return taskDTO;
    }

    @Override
    public Task toEntity(CreateTaskDTO createTaskDTO) {
        if ( createTaskDTO == null ) {
            return null;
        }

        Task.TaskBuilder<?, ?> task = Task.builder();

        task.name( createTaskDTO.getName() );
        task.description( createTaskDTO.getDescription() );

        return task.build();
    }

    @Override
    public void updateTaskFromDTO(UpdateTaskDTO updateTaskDTO, Task task) {
        if ( updateTaskDTO == null ) {
            return;
        }

        if ( updateTaskDTO.getName() != null ) {
            task.setName( updateTaskDTO.getName() );
        }
        if ( updateTaskDTO.getDescription() != null ) {
            task.setDescription( updateTaskDTO.getDescription() );
        }
    }

    protected List<TaskDTO> taskListToTaskDTOList(List<Task> list) {
        if ( list == null ) {
            return null;
        }

        List<TaskDTO> list1 = new ArrayList<TaskDTO>( list.size() );
        for ( Task task : list ) {
            list1.add( toDTO( task ) );
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
