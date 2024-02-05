package com.ApiProject.TaskManagementSystem.Service;

import com.ApiProject.TaskManagementSystem.Entities.Task;
import com.ApiProject.TaskManagementSystem.Entities.User;
import com.ApiProject.TaskManagementSystem.Enums.Status;
import com.ApiProject.TaskManagementSystem.Exceptions.TaskNotFoundException;
import com.ApiProject.TaskManagementSystem.Exceptions.UserNotFoundException;
import com.ApiProject.TaskManagementSystem.Repository.TaskRepository;
import com.ApiProject.TaskManagementSystem.Repository.UserRepository;
import com.ApiProject.TaskManagementSystem.ServiceIMPL.TaskServiceIMPL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class TaskService1 implements TaskServiceIMPL {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger log= LoggerFactory.getLogger(TaskService1.class);
    @Override
    public String addTask(Task task) {
        taskRepository.save(task);
        log.info("task is created");
        return "successfully Added task";
    }

    @Override
    public String assignTaskToUsers(Integer taskId, List<Long> listOfUserIds)throws Exception {
        Optional<Task> optionalTask=taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("task with "+taskId+" not found");
        }
        Task task=optionalTask.get();
        for(Long userId:listOfUserIds){
            Optional<User> optionalUser=userRepository.findByUserId(userId);
            if(optionalUser.isEmpty()){
                throw new UserNotFoundException("User not found with id "+userId);
            }
            User user=optionalUser.get();
            user.getTaskList().add(task);
        }
        log.info("Task has been assigned to the users");
        return "Successfully assigned te task to users";
    }

    @Override
    public Task updateTaskByTaskId(Task task, Integer taskId)throws Exception {
        Optional<Task> optionalTask=taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("task with "+taskId+" not found");
        }
        Task currTask=optionalTask.get();
        currTask.setTaskStatus(task.getTaskStatus());
        currTask.setTitle(task.getTitle());
        currTask.setDescription(task.getDescription());
        currTask.setDueDate(task.getDueDate());
        log.info("Task updated successfully. Task ID: {}", taskId);
        return currTask;
    }

    @Override
    public String deleteTaskById(Integer taskId)throws Exception {
        Optional<Task> optionalTask=taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("task with "+taskId+" not found");
        }
        taskRepository.deleteById(taskId);
        log.info("task with id {} is removed",taskId);
        return "task is removed";
    }

    @Override
    public Task getTaskById(Integer taskId) throws Exception{
        Optional<Task> optionalTask=taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            throw new TaskNotFoundException("task with "+taskId+" not found");
        }
        Task currTask=optionalTask.get();
        log.info("task is fetched {}",currTask);
        return currTask;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> taskList=taskRepository.findAll();
        log.info("fetched all the tasks");
        return taskList;
    }

    @Override
    public List<Task> getTaskByStatus(Status status) {
        List<Task> taskList=taskRepository.findAll();
        List<Task> statusTaskList=new ArrayList<>();
        for(Task task:taskList){
            if(task.getTaskStatus()==status)statusTaskList.add(task);
        }
        log.info("fetched tasks with status {}",status);
        return statusTaskList;
    }

    @Override
    public List<User> getUsersWithTaskId(Integer taskId){
        List<User> userList=userRepository.findAll();
        List<User> userListWithTaskId=new ArrayList<>();
        for(User user:userList){
            List<Task> tasks=user.getTaskList();
            for(Task task:tasks){
                if(Objects.equals(task.getTaskId(), taskId)){
                    userListWithTaskId.add(user);
                }
            }
        }
        log.info("fetched all the users with taskId {}",taskId);
        return userListWithTaskId;
    }

    @Override
    public List<Task> getTasksDueDateBefore(LocalDate date) {
        return taskRepository.findByDueDateBefore(date);
    }
}
