package com.ApiProject.TaskManagementSystem;

import com.ApiProject.TaskManagementSystem.Entities.Task;
import com.ApiProject.TaskManagementSystem.Entities.User;
import com.ApiProject.TaskManagementSystem.Enums.Status;
import com.ApiProject.TaskManagementSystem.Exceptions.TaskNotFoundException;
import com.ApiProject.TaskManagementSystem.Exceptions.UserNotFoundException;
import com.ApiProject.TaskManagementSystem.Repository.TaskRepository;
import com.ApiProject.TaskManagementSystem.Repository.UserRepository;
import com.ApiProject.TaskManagementSystem.Service.TaskService1;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskApisTesting {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService1 taskService;

    @Test
    void testAddTask() {
        // Mock data for testing
        Task task = Task.builder()
                .taskId(1)
                .title("Test Task")
                .description("Test Description")
                .createdDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(7))
                .taskStatus(Status.PROGRESS)
                .build();
        // Mock the behavior of the taskRepository.save method
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        // Call the method to test
        String result = taskService.addTask(task);

        // Verify that the taskRepository.save method was called with the correct argument
        verify(taskRepository, times(1)).save(task);

        // Assert the result
        assertEquals("successfully Added task", result);
    }

    @Test
    void testAssignTaskToUsersTaskNotFound() {
        // Mock the behavior of taskRepository.findById to return an empty Optional
        when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

        // Call the method to test and expect TaskNotFoundException
        assertThrows(TaskNotFoundException.class, () -> taskService.assignTaskToUsers(1, new ArrayList<>()));
    }

    @Test
    void testAssignTaskToUsersUserNotFound() {
        // Mock the behavior of taskRepository.findById to return a Task
        when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(new Task()));

        // Mock the behavior of userRepository.findByUserId to return an empty Optional
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // Call the method to test and expect UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> taskService.assignTaskToUsers(1, List.of(1L)));
    }
    @Test
    void testUpdateTaskByTaskIdTaskNotFound() {
        // Mock the behavior of taskRepository.findById to return an empty Optional
        when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

        // Call the method to test and expect TaskNotFoundException
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTaskByTaskId(new Task(), 1));
    }
    @Test
    void testDeleteTaskById() throws Exception {
        // Mock data for testing
        Task existingTask = Task.builder()
                .taskId(1)
                .title("Existing Task")
                .description("Existing Description")
                .build();

        // Mock the behavior of taskRepository.findById
        when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(existingTask));

        // Call the method to test
        String result = taskService.deleteTaskById(1);

        // Verify that the taskRepository.findById and taskRepository.deleteById methods were called with the correct arguments
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).deleteById(1);

        // Assert the result
        assertEquals("task is removed", result);
    }

    @Test
    void testDeleteTaskByIdTaskNotFound() {
        // Mock the behavior of taskRepository.findById to return an empty Optional
        when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

        // Call the method to test and expect TaskNotFoundException
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(1));
    }
    @Test
    void testGetTaskById() throws Exception {
        // Mock data for testing
        Task existingTask = Task.builder()
                .taskId(1)
                .title("Existing Task")
                .description("Existing Description")
                .build();

        // Mock the behavior of taskRepository.findById
        when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(existingTask));

        // Call the method to test
        Task result = taskService.getTaskById(1);

        // Verify that the taskRepository.findById method was called with the correct argument
        verify(taskRepository, times(1)).findById(1);

        // Assert the result
        assertEquals(existingTask, result);
    }

    @Test
    void testGetTaskByIdTaskNotFound() {
        // Mock the behavior of taskRepository.findById to return an empty Optional
        when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

        // Call the method to test and expect TaskNotFoundException
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1));
    }
    @Test
    void testGetAllTasks() {
        // Mock data for testing
        Task task1 = Task.builder()
                .taskId(1)
                .title("Task 1")
                .description("Description 1")
                .build();

        Task task2 = Task.builder()
                .taskId(2)
                .title("Task 2")
                .description("Description 2")
                .build();

        List<Task> mockTaskList = Arrays.asList(task1, task2);

        // Mock the behavior of taskRepository.findAll
        when(taskRepository.findAll()).thenReturn(mockTaskList);

        // Call the method to test
        List<Task> result = taskService.getAllTasks();

        // Verify that the taskRepository.findAll method was called
        verify(taskRepository, times(1)).findAll();

        // Assert the result
        assertEquals(mockTaskList, result);
    }
    @Test
    void testGetTaskByStatus() {
        // Mock data for testing
        Task task1 = Task.builder()
                .taskId(1)
                .title("Task 1")
                .description("Description 1")
                .taskStatus(Status.PROGRESS)
                .build();

        Task task2 = Task.builder()
                .taskId(2)
                .title("Task 2")
                .description("Description 2")
                .taskStatus(Status.COMPLETED)
                .build();

        List<Task> mockTaskList = Arrays.asList(task1, task2);

        // Mock the behavior of taskRepository.findAll
        when(taskRepository.findAll()).thenReturn(mockTaskList);

        // Call the method to test
        List<Task> result = taskService.getTaskByStatus(Status.PROGRESS);

        // Verify that the taskRepository.findAll method was called
        verify(taskRepository, times(1)).findAll();

        // Assert the result
        assertEquals(Arrays.asList(task1), result);
    }
    @Test
    void testGetUsersWithTaskId() {
        // Mock data for testing
        Task task1 = Task.builder()
                .taskId(1)
                .title("Task 1")
                .description("Description 1")
                .build();

        Task task2 = Task.builder()
                .taskId(2)
                .title("Task 2")
                .description("Description 2")
                .build();

        User user1 = User.builder()
                .userId(1L)
                .userName("User 1")
                .userPassword("password1")
                .taskList(Arrays.asList(task1, task2))
                .build();

        User user2 = User.builder()
                .userId(2L)
                .userName("User 2")
                .userPassword("password2")
                .taskList(Arrays.asList(task1))
                .build();

        List<User> mockUserList = Arrays.asList(user1, user2);

        // Mock the behavior of userRepository.findAll
        when(userRepository.findAll()).thenReturn(mockUserList);

        // Call the method to test
        List<User> result = taskService.getUsersWithTaskId(1);

        // Verify that the userRepository.findAll method was called
        verify(userRepository, times(1)).findAll();

        // Assert the result
        assertEquals(Arrays.asList(user1, user2), result);
    }
    @Test
    void testGetAllTasksPaginated() {
        // Mock data for testing
        Task task1 = Task.builder()
                .taskId(1)
                .title("Task 1")
                .description("Description 1")
                .build();

        Task task2 = Task.builder()
                .taskId(2)
                .title("Task 2")
                .description("Description 2")
                .build();

        Task task3 = Task.builder()
                .taskId(3)
                .title("Task 3")
                .description("Description 3")
                .build();

        Page<Task> mockTaskPage = Mockito.mock(Page.class);
        when(mockTaskPage.getContent()).thenReturn(Arrays.asList(task1, task2, task3));

        // Mock the behavior of taskRepository.findAll
        when(taskRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(mockTaskPage);

        // Call the method to test
        Page<Task> result = taskService.getAllTasksPaginated(0, 3);

        // Verify that the taskRepository.findAll method was called with the correct argument
        verify(taskRepository, times(1)).findAll(PageRequest.of(0, 3));

        // Assert the result
        assertEquals(Arrays.asList(task1, task2, task3), result.getContent());
    }

}
