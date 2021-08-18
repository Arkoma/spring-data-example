package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void userServiceIsInjectedIntoApplicationContextAsABean() {
        assertTrue(applicationContext.containsBean("userService"));
    }

    @Test
    public void userServiceInjectsUserRepository() {
        UserService userService = (UserService) applicationContext.getBean("userService");
        UserRepository userRepository = (UserRepository) ReflectionTestUtils.getField(userService, "userRepository");
        assertNotNull(userRepository);
    }
}
