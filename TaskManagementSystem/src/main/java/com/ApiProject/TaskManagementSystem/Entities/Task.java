package com.ApiProject.TaskManagementSystem.Entities;

import com.ApiProject.TaskManagementSystem.Enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="task_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="task_id",nullable = false)
    Integer taskId;

    @Column(name="task_title",nullable = false,unique = true)
    String title;

    @Column(name="description",columnDefinition = "text")
    String description;

    @CreationTimestamp
    LocalDate createdDate;

    @Column(name="due_date",nullable = false)
    LocalDate dueDate;

    @Column(name="task_status",nullable = false)
    @Enumerated(value=EnumType.STRING)
    Status taskStatus;

    @ManyToMany
    @JoinTable
    List<User> userList=new ArrayList<>();
}
