package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("new/{firstName}/{lastName}/{email}")
    public User saveNewUser(@PathVariable String firstName,
                            @PathVariable String lastName,
                            @PathVariable String email) {
        return this.userService.saveUser(firstName, lastName, email);
    }
}
