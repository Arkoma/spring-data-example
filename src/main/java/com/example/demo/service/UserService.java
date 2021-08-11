package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository) { this.userRepository = userRepository; }

    public User saveUser(String firstName, String lastname, String email) {
        return this.userRepository.save(new User(firstName, lastname, email));
    }
}
