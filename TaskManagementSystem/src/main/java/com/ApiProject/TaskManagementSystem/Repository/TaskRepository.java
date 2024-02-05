package com.ApiProject.TaskManagementSystem.Repository;

import com.ApiProject.TaskManagementSystem.Entities.Task;
import com.ApiProject.TaskManagementSystem.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    //List<Task> findByStatus(Status status);
    List<Task> findByDueDateBefore(LocalDate date);
}
