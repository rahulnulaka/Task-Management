package com.ApiProject.TaskManagementSystem.ServiceIMPL;
import com.ApiProject.TaskManagementSystem.DTO.TaskStatus;
import com.ApiProject.TaskManagementSystem.Entities.Task;
import com.ApiProject.TaskManagementSystem.Entities.User;
import com.ApiProject.TaskManagementSystem.Exceptions.UserNotFoundException;

import java.util.List;

public interface UserServiceIMPL {
    String addUser(User user);
    String changePass(Long id,String oldPass,String newPass) throws UserNotFoundException;

    String changeUserName(Long userId, String userNewName) throws UserNotFoundException;

    List<Task> getTaskByid(Long userId) throws UserNotFoundException;

    String changeTaskStatus(Long userId, Integer taskId) throws Exception;

    List<TaskStatus> getAllTasksStatusOfUser(Long userId) throws Exception;

    String deleteUser(Long userId) throws Exception;
}
