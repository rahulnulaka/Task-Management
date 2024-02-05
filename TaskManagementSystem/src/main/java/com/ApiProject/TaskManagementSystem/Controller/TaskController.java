package com.ApiProject.TaskManagementSystem.Controller;

import com.ApiProject.TaskManagementSystem.DTO.ListDto;
import com.ApiProject.TaskManagementSystem.Entities.Task;
import com.ApiProject.TaskManagementSystem.Entities.User;
import com.ApiProject.TaskManagementSystem.Enums.Status;
import com.ApiProject.TaskManagementSystem.Service.TaskService1;
import com.ApiProject.TaskManagementSystem.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("task")
@PreAuthorize("hasRole('ADMIN')")
public class TaskController {

    @Autowired
    private UserService userServiceIMPL;
    @Autowired
    private TaskService1 taskServiceIMPL;

    private static final Logger log= LoggerFactory.getLogger(TaskController.class);

    @GetMapping("welcome")
    public String getWelcome(){
        return "Hi welcome";
    }
    @PostMapping("/addTask")
    public ResponseEntity<Object> addTask(@RequestBody Task task){
        String result=taskServiceIMPL.addTask(task);
        log.info("task has been added successfully {}:",task);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/assignTask/{taskId}")
    public ResponseEntity<Object> assignTaskToUsers(@PathVariable Integer taskId,
                                                    @RequestBody ListDto listDto)throws Exception{
        List<Long> listOfUserIds=listDto.getUserIds();
        try{
            String result=taskServiceIMPL.assignTaskToUsers(taskId,listOfUserIds);
            log.info("Task has been assigned to given users {}:",listOfUserIds);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("Error occured while assigning task to the users {}:",message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateTask/{taskId}")
    public ResponseEntity<Object> updateTask(@RequestBody Task task,
                                             @PathVariable Integer taskId)throws Exception{
        try{
            Task updatedTask=taskServiceIMPL.updateTaskByTaskId(task,taskId);
            log.info("task has been updated {}:",updatedTask);
            return new ResponseEntity<>(updatedTask,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("Error occured while updating the task {}:{}",task,message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteTask/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable Integer taskId)throws Exception{
        try{
            String result=taskServiceIMPL.deleteTaskById(taskId);
            log.info("task with taskId {} has been deleted successfully",taskId);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("Error occured while deleting the task with id{}:{}",taskId,message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long userId)throws Exception{
        try{
            String result=userServiceIMPL.deleteUser(userId);
            log.info("Successfully eleminated user with userId {}:",userId);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("No user found with userId {}:{}",userId,message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getTaskById/{taskId}")
    public ResponseEntity<Object> getTaskById(@PathVariable Integer taskId)throws Exception{
        try{
            Task task=taskServiceIMPL.getTaskById(taskId);
            log.info("task with taskId {} has been deleted successfully",taskId);
            return new ResponseEntity<>(task,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("Error occured while retrieving the task with id{}:{}",taskId,message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> taskList=taskServiceIMPL.getAllTasks();
        log.info("successfully retrieved all the tasks");
        return new ResponseEntity<>(taskList,HttpStatus.OK);
    }

    @GetMapping("/getTaskByStatus")
    public ResponseEntity<List<Task>> getTaskByStatus(@RequestParam Status status){
        List<Task> taskList=taskServiceIMPL.getTaskByStatus(status);
        log.info("Generated tasks with status {}:",status);
        return new ResponseEntity<>(taskList,HttpStatus.OK);
    }

    @GetMapping("/getUsersWithTaskId/{taskId}")
    public ResponseEntity<Object> getUsersWithTaskId(@PathVariable Integer taskId)throws Exception{
        try{
            List<User> userList=taskServiceIMPL.getUsersWithTaskId(taskId);
            log.info("Generated users with taskId {}:",taskId);
            return new ResponseEntity<>(userList,HttpStatus.OK);
        }
        catch(Exception message){
            log.error("Error occured while generating users with taskId {}:{}",taskId,message.getMessage());
            return new ResponseEntity<>(message.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTasksDueDateBefore")
    public ResponseEntity<List<Task>> getTasksDueBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        List<Task> taskList=taskServiceIMPL.getTasksDueDateBefore(date);
        return new ResponseEntity<>(taskList,HttpStatus.OK);
    }
    @GetMapping("/getAllTasksPaginated")
    public ResponseEntity<Page<Task>> getAllTasksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Task> tasks = taskServiceIMPL.getAllTasksPaginated(page, size);
        log.info("Retrieved paginated tasks: {}", tasks);
        return ResponseEntity.ok(tasks);
    }
}
