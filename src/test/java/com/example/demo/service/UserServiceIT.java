package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void userServiceIsABeanOnAppCtx() {
        assertTrue(applicationContext.containsBean("userService"));
    }

    @Test
    void userServiceInjectsUserRepository() {
        UserRepository injectedUserRepository = (UserRepository) ReflectionTestUtils.getField(userService, "userRepository");
        assertSame(userRepository, injectedUserRepository);
    }
}
