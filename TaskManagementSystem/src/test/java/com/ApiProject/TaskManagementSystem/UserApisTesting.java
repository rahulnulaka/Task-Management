package com.ApiProject.TaskManagementSystem;

import com.ApiProject.TaskManagementSystem.DTO.TaskStatus;
import com.ApiProject.TaskManagementSystem.Entities.Task;
import com.ApiProject.TaskManagementSystem.Entities.User;
import com.ApiProject.TaskManagementSystem.Enums.Status;
import com.ApiProject.TaskManagementSystem.Exceptions.TaskNotFoundException;
import com.ApiProject.TaskManagementSystem.Exceptions.UserNotFoundException;
import com.ApiProject.TaskManagementSystem.Repository.TaskRepository;
import com.ApiProject.TaskManagementSystem.Repository.UserRepository;
import com.ApiProject.TaskManagementSystem.Service.TaskService1;
import com.ApiProject.TaskManagementSystem.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
public class UserApisTesting {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testAddUser() {
        // Mock data for testing
        User user = User.builder()
                .userId(1L)
                .userName("TestUser")
                .userPassword("TestPassword")
                .build();

        // Mock the behavior of userRepository.save
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Call the method to test
        String result = userService.addUser(user);

        // Verify that the userRepository.save method was called with the correct argument
        verify(userRepository, times(1)).save(user);

        // Assert the result
        assertEquals("user saved successfully", result);
    }
    @Test
    void testChangePass() throws UserNotFoundException {
        // Mock data for testing
        User user = User.builder()
                .userId(1L)
                .userName("TestUser")
                .userPassword("OldPassword")
                .build();

        // Mock the behavior of userRepository.findByUserId
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        // Call the method to test
        String result = userService.changePass(1L, "OldPassword", "NewPassword");

        // Verify that the userRepository.findByUserId and userRepository.save methods were called with the correct arguments
        verify(userRepository, times(1)).findByUserId(1L);
        verify(userRepository, times(1)).save(Mockito.any(User.class));

        // Assert the result
        assertEquals("password has been changed successfully", result);
    }

    @Test
    void testChangePassUserNotFound() {
        // Mock the behavior of userRepository.findByUserId to return an empty Optional
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // Call the method to test and expect UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.changePass(1L, "OldPassword", "NewPassword"));
    }

    @Test
    void testChangePassWrongPassword() throws UserNotFoundException {
        // Mock data for testing
        User user = User.builder()
                .userId(1L)
                .userName("TestUser")
                .userPassword("OldPassword")
                .build();

        // Mock the behavior of userRepository.findByUserId
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        // Call the method to test
        String result = userService.changePass(1L, "WrongPassword", "NewPassword");

        // Verify that the userRepository.findByUserId method was called with the correct argument
        verify(userRepository, times(1)).findByUserId(1L);

        // Assert the result
        assertEquals("wrong password", result);
    }
    @Test
    void testChangeUserName() throws UserNotFoundException {
        // Mock data for testing
        User user = User.builder()
                .userId(1L)
                .userName("OldUserName")
                .userPassword("Password")
                .build();

        // Mock the behavior of userRepository.findByUserId
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        // Call the method to test
        String result = userService.changeUserName(1L, "NewUserName");

        // Verify that the userRepository.findByUserId and userRepository.save methods were called with the correct arguments
        verify(userRepository, times(1)).findByUserId(1L);
        verify(userRepository, times(1)).save(Mockito.any(User.class));

        // Assert the result
        assertEquals("User Name changed successfully", result);
        assertEquals("NewUserName", user.getUserName());
    }

    @Test
    void testChangeUserNameUserNotFound() {
        // Mock the behavior of userRepository.findByUserId to return an empty Optional
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // Call the method to test and expect UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.changeUserName(1L, "NewUserName"));
    }
    @Test
    void testGetTaskByid() throws UserNotFoundException {
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

        User user = User.builder()
                .userId(1L)
                .userName("TestUser")
                .userPassword("TestPassword")
                .taskList(List.of(task1, task2))
                .build();

        // Mock the behavior of userRepository.findByUserId
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        // Call the method to test
        List<Task> result = userService.getTaskByid(1L);

        // Verify that the userRepository.findByUserId method was called with the correct argument
        verify(userRepository, times(1)).findByUserId(1L);

        // Assert the result
        assertEquals(List.of(task1, task2), result);
    }

    @Test
    void testGetTaskByidUserNotFound() {
        // Mock the behavior of userRepository.findByUserId to return an empty Optional
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // Call the method to test and expect UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.getTaskByid(1L));
    }
    @Test
    void testChangeTaskStatus() throws Exception {
        // Mock data for testing
        Task task1 = Task.builder()
                .taskId(1)
                .title("Task 1")
                .description("Description 1")
                .taskStatus(Status.PENDING)
                .build();

        Task task2 = Task.builder()
                .taskId(2)
                .title("Task 2")
                .description("Description 2")
                .taskStatus(Status.PROGRESS)
                .build();

        User user = User.builder()
                .userId(1L)
                .userName("TestUser")
                .userPassword("TestPassword")
                .taskList(List.of(task1, task2))
                .build();

        // Mock the behavior of userRepository.findByUserId
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        // Call the method to test
        String result = userService.changeTaskStatus(1L, 1);

        // Verify that the userRepository.findByUserId and userRepository.save methods were called with the correct arguments
        verify(userRepository, times(1)).findByUserId(1L);
        verify(userRepository, times(1)).save(Mockito.any(User.class));

        // Assert the result
        assertEquals("TaskStatus changed successfully", result);
        assertEquals(Status.COMPLETED, task1.getTaskStatus());
    }

    @Test
    void testChangeTaskStatusUserNotFound() {
        // Mock the behavior of userRepository.findByUserId to return an empty Optional
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // Call the method to test and expect UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.changeTaskStatus(1L, 1));
    }

    @Test
    void testChangeTaskStatusTaskNotFound() {
        // Mock data for testing
        Task task1 = Task.builder()
                .taskId(1)
                .title("Task 1")
                .description("Description 1")
                .taskStatus(Status.PENDING)
                .build();

        User user = User.builder()
                .userId(1L)
                .userName("TestUser")
                .userPassword("TestPassword")
                .taskList(List.of(task1))
                .build();

        // Mock the behavior of userRepository.findByUserId
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        // Call the method to test and expect TaskNotFoundException
        assertThrows(TaskNotFoundException.class, () -> userService.changeTaskStatus(1L, 2));
    }

    @Test
    void testChangeTaskStatusInvalidStatus() throws Exception {
        // Mock data for testing
        Task task1 = Task.builder()
                .taskId(1)
                .title("Task 1")
                .description("Description 1")
                .taskStatus(Status.COMPLETED)
                .build();

        User user = User.builder()
                .userId(1L)
                .userName("TestUser")
                .userPassword("TestPassword")
                .taskList(List.of(task1))
                .build();

        // Mock the behavior of userRepository.findByUserId
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        // Call the method to test
        String result = userService.changeTaskStatus(1L, 1);

        // Verify that the userRepository.findByUserId method was called with the correct argument
        verify(userRepository, times(1)).findByUserId(1L);

        // Assert the result
        assertEquals("TaskStatus changed successfully", result);
        assertEquals(Status.COMPLETED, task1.getTaskStatus());
    }
    @Test
    void testGetAllTasksStatusOfUserUserNotFound() {
        // Mock the behavior of userRepository.findByUserId to return an empty Optional
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // Call the method to test and expect UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.getAllTasksStatusOfUser(1L));
    }
    @Test
    void testDeleteUser() throws Exception {
        // Mock data for testing
        User user = User.builder()
                .userId(1L)
                .userName("TestUser")
                .userPassword("TestPassword")
                .build();

        // Mock the behavior of userRepository.findByUserId
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        // Call the method to test
        String result = userService.deleteUser(1L);

        // Verify that the userRepository.findByUserId and userRepository.deleteByUserId methods were called with the correct arguments
        verify(userRepository, times(1)).findByUserId(1L);
        verify(userRepository, times(1)).deleteByUserId(1L);

        // Assert the result
        assertEquals("successfully deleted user", result);
    }

    @Test
    void testDeleteUserUserNotFound() {
        // Mock the behavior of userRepository.findByUserId to return an empty Optional
        when(userRepository.findByUserId(Mockito.any(Long.class))).thenReturn(Optional.empty());

        // Call the method to test and expect UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
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
        Page<Task> result = userService.getAllTasksPaginated(0, 3);

        // Assert the result
        assertEquals(Arrays.asList(task1, task2, task3), result.getContent());
    }
}
