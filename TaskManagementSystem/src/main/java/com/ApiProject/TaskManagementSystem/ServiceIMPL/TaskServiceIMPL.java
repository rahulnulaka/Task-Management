package com.ApiProject.TaskManagementSystem.ServiceIMPL;

import com.ApiProject.TaskManagementSystem.Entities.Task;
import com.ApiProject.TaskManagementSystem.Entities.User;
import com.ApiProject.TaskManagementSystem.Enums.Status;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface TaskServiceIMPL {
    String addTask(Task task);

    String assignTaskToUsers(Integer taskId, List<Long> listOfUserIds) throws Exception;

    Task updateTaskByTaskId(Task task, Integer taskId) throws Exception;

    String deleteTaskById(Integer taskId) throws Exception;

    Task getTaskById(Integer taskId) throws Exception;

    List<Task> getAllTasks();

    List<Task> getTaskByStatus(Status status);

    List<User> getUsersWithTaskId(Integer taskId);

    List<Task> getTasksDueDateBefore(LocalDate date);

    Page<Task> getAllTasksPaginated(int page, int size);
}
