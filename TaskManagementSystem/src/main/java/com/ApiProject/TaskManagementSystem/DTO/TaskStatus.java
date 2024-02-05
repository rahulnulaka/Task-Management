package com.ApiProject.TaskManagementSystem.DTO;

import com.ApiProject.TaskManagementSystem.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatus {
    private String title;

    private Status status;
}
