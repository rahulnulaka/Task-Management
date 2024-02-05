package com.ApiProject.TaskManagementSystem.Service;

import com.ApiProject.TaskManagementSystem.DTO.TaskStatus;
import com.ApiProject.TaskManagementSystem.Entities.Task;
import com.ApiProject.TaskManagementSystem.Entities.User;
import com.ApiProject.TaskManagementSystem.Enums.Status;
import com.ApiProject.TaskManagementSystem.Exceptions.TaskNotFoundException;
import com.ApiProject.TaskManagementSystem.Exceptions.UserNotFoundException;
import com.ApiProject.TaskManagementSystem.Repository.UserRepository;
import com.ApiProject.TaskManagementSystem.ServiceIMPL.UserServiceIMPL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserServiceIMPL {

    @Autowired
    private UserRepository userRepository;

    private static final Logger log= LoggerFactory.getLogger(UserService.class);
    @Override
    public String addUser(User user) {
        userRepository.save(user);
        log.info("user saved successfully into the db",user);
        return "user saved successfully";
    }

    @Override
    public String changePass(Long id, String oldPass, String newPass) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findByUserId(id);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found with id"+id);
        }
        User user=optionalUser.get();
        if(user.getUserPassword().equals(oldPass)){
            user.setUserPassword(newPass);
            log.info("user password has been changed successfully");
            return "password has been changed successfully";
        }
        log.error("user has entered wrong password {}",user);
        return "wrong password";
    }

    @Override
    public String changeUserName(Long userId, String userNewName) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){

            throw new UserNotFoundException("user not found with id:"+userId);
        }
        User user=optionalUser.get();
        user.setUserName(userNewName);
        log.info("user name has been changed successfully");
        return "User Name changed successfully";
    }

    @Override
    public List<Task> getTaskByid(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser=userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("user not found with id:"+userId);
        }
        User user=optionalUser.get();
        List<Task> taskList=user.getTaskList();
        log.info("retrieved tasks of the user successfully");
        return taskList;
    }

    @Override
    public String changeTaskStatus(Long userId, Integer taskId)throws Exception{
        Optional<User> optionalUser=userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("user not found with id:"+userId);
        }
        User user=optionalUser.get();
        List<Task> taskList=user.getTaskList();
        Task currentTask=null;
        for(Task task:taskList){
            if(task.getTaskId()==taskId){
                currentTask=task;
                break;
            }
        }
        if(currentTask==null){
            throw new TaskNotFoundException("Task with taskId is not present in the taskList of user"+taskId);
        }
        HashSet<String> taskStatus=new HashSet<>();
        Collections.addAll(taskStatus,"PENDING","PROGRESS");

        if(taskStatus.contains(currentTask.getTaskStatus().toString())){
            currentTask.setTaskStatus(Status.COMPLETED);
        }
        log.info("TaskStatus of the user task has been updated successfully");
        return "TaskStatus changed successfully";
    }

    @Override
    public List<TaskStatus> getAllTasksStatusOfUser(Long userId) throws Exception{
        Optional<User> optionalUser=userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){

            throw new UserNotFoundException("user not found with id:"+userId);
        }
        User user=optionalUser.get();
        List<Task> taskList=user.getTaskList();
        List<TaskStatus> taskStatusList=new ArrayList<>();
        for(Task task:taskList){
            taskStatusList.add(new TaskStatus(task.getTitle(),task.getTaskStatus()));
        }
        log.info("Retrieved all tasks status of the user");
        return taskStatusList;
    }

    @Override
    public String deleteUser(Long userId)throws Exception {
        Optional<User> optionalUser=userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){

            throw new UserNotFoundException("user not found with id:"+userId);
        }
        User user=optionalUser.get();
        userRepository.deleteByUserId(userId);
        log.info("user with id {} deleted successfully",userId);
        return "successfully deleted user";
    }
}
