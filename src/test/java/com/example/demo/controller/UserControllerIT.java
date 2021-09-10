package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@WebAppConfiguration
class UserControllerIT {

    private final WebApplicationContext webApplicationContext;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserController userController;

    @Autowired
    public UserControllerIT(WebApplicationContext webApplicationContext, UserRepository userRepository, UserService userService, UserController userController) {
        this.webApplicationContext = webApplicationContext;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userController = userController;
    }

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.userRepository.deleteAll();
    }

    @Test
    void userControllerIsABeanOnApplicationContext() {
        assertTrue(this.webApplicationContext.containsBean("userController"));
    }

    @Test
    void userControllerHasNewUserEndpoint() throws Exception {
        this.mockMvc.perform(post("/new/Bob/Dole/wannabepres@example.com"))
                .andExpect(jsonPath("$.firstName").value("Bob"))
                .andExpect(jsonPath("$.lastName").value("Dole"))
                .andExpect(jsonPath("$.email").value("wannabepres@example.com"))
                .andReturn();
    }

    @Test
    void userControllerInjectsUserService() {
        UserService injectedUserService = (UserService) ReflectionTestUtils.getField(userController, "userService");
        assertSame(this.userService, injectedUserService);
    }

    @Test
    void newUserControllerSavesUser() throws Exception {
        this.mockMvc.perform(post("/new/Bob/Dole/wannabepres@example.com"))
                .andExpect(jsonPath("$.firstName").value("Bob"))
                .andExpect(jsonPath("$.lastName").value("Dole"))
                .andExpect(jsonPath("$.email").value("wannabepres@example.com"))
                .andReturn();
        Optional<User> user = this.userRepository.findByFirstName("Bob");
        assertAll(() -> {
            assertTrue(user.isPresent());
            assertEquals("Dole", user.get().getLastName());
        });
    }


}