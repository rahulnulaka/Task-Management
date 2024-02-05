package com.ApiProject.TaskManagementSystem.SecurityConfig;

import com.ApiProject.TaskManagementSystem.Entities.User;
import com.ApiProject.TaskManagementSystem.Exceptions.UserNotFoundException;
import com.ApiProject.TaskManagementSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User)userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found username: "+username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getUserPassword())
                .roles("USER")
                .build();
    }
}