package com.example.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public  void saveUserIfNotExists(String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User newUser = new User();
            newUser.setUsername(username);
            userRepository.save(newUser);
        }
    }
}
