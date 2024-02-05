package com.ApiProject.TaskManagementSystem.Repository;

import com.ApiProject.TaskManagementSystem.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserId(Long id);

    void deleteByUserId(Long userId);
}
