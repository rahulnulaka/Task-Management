package com.ApiProject.TaskManagementSystem.Controller;

import com.ApiProject.TaskManagementSystem.DTO.TaskStatus;
import com.ApiProject.TaskManagementSystem.Entities.Task;
import com.ApiProject.TaskManagementSystem.Entities.User;
import com.ApiProject.TaskManagementSystem.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userServiceIMPL;

    private static final Logger log= LoggerFactory.getLogger(UserController.class);


    @PostMapping("/addUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addUser(@RequestBody User user){
        String res=userServiceIMPL.addUser(user);
        log.info("Created user successfully with userId: {}",user.getUserName());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PutMapping("changePass/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> changePass(@PathVariable Long userId,
                                             @RequestParam String userOldPassword,
                                             @RequestParam String UserNewPassword)throws Exception{
        try {
            String result = userServiceIMPL.changePass(userId, userOldPassword, UserNewPassword);
            log.info("user password has been changed successfully");
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("Error ocured while changing the password for user {}:",message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("changeUserName/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> changeUserName(@PathVariable Long userId,
                                                 @RequestParam String userNewName)throws Exception{
        try {
            String result=userServiceIMPL.changeUserName(userId,userNewName);
            log.info("username changed successfully");
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("error occured while changing the username {}:",message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTasksById/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getTasksById(@PathVariable Long userId)throws Exception{
        try {
            List<Task> taskList=userServiceIMPL.getTaskByid(userId);
            log.info("Generating tasks for the user with id:",userId);
            return new ResponseEntity<>(taskList,HttpStatus.OK);
        }
        catch (Exception message){
            log.error("Error occured while retriveing the tasks of the user",message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/changeTaskStatus/{userId}/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> changeTaskStatus(@PathVariable Long userId,
                                                   @PathVariable Integer taskId){
        try{
            String result=userServiceIMPL.changeTaskStatus(userId,taskId);
            log.info("Successfully changed status of the user task with userId and taskId {}:{}",userId,taskId);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("Error occured while changing the status of the task",message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllTaskStatusOfUser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllTaskStatusOfUser(@PathVariable Long userId)throws Exception{
        try{
            List<TaskStatus> taskStatusList=userServiceIMPL.getAllTasksStatusOfUser(userId);
            log.info("Generated all the tasks status of the user with userId {}:",userId);
            return new ResponseEntity<>(taskStatusList,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("Error occured while getting the status of the tasks of the user with userId {}:{}",userId,message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
