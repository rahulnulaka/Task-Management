package com.ApiProject.TaskManagementSystem.Entities;

import com.ApiProject.TaskManagementSystem.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="User_id",nullable = false)
    Long userId;


    @Column(name="user_name",nullable = false, unique = true)
    String userName;

    @Column(name="user_password",nullable = false)
    String userPassword;

    @Column(name="role",nullable = false)
    @Enumerated(value=EnumType.STRING)
    Role userRole;

    @ManyToMany(mappedBy = "userList",cascade = CascadeType.ALL)
    List<Task> taskList=new ArrayList<>();
}
