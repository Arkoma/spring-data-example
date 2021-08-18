package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@WebAppConfiguration
class UserControllerIT {

    private final ApplicationContext applicationContext;
    private final WebApplicationContext webApplicationContext;
    private final UserRepository userRepository;

    @Autowired
    public UserControllerIT(ApplicationContext applicationContext,
                            WebApplicationContext webApplicationContext,
                            UserRepository userRepository) {
        this.applicationContext = applicationContext;
        this.webApplicationContext = webApplicationContext;
        this.userRepository = userRepository;
    }

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
       this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
       this.userRepository.deleteAll();
    }

    @Test
    public void userControllerIsControllerOnApplicationContext() {
        assertTrue(this.applicationContext.containsBean("userController"));
    }


    @Test
    public void userControllerHasNewUserEndpoint() throws Exception {
        this.mockMvc.perform(post("/new/Bob/Dole/wannabepres@example.com"))
                .andExpect(jsonPath("$.firstName").value("Bob"))
                .andExpect(jsonPath("$.lastName").value("Dole"))
                .andExpect(jsonPath("$.email").value("wannabepres@example.com"))
                .andReturn();
    }

    @Test
    public void userControllerInjectsUserService() {
        UserController userController = (UserController) applicationContext.getBean("userController");
        UserService userService = (UserService) ReflectionTestUtils.getField(userController, "userService");
        assertNotNull(userService);
    }

    @Test
    public void userControllerSavesUser() throws Exception {
        this.mockMvc.perform(post("/new/Bob/Dole/wannabepres@example.com"))
                .andExpect(jsonPath("$.firstName").value("Bob"))
                .andExpect(jsonPath("$.lastName").value("Dole"))
                .andExpect(jsonPath("$.email").value("wannabepres@example.com"))
                .andReturn();
        this.userRepository.findByFirstName("Bob").ifPresent(u -> assertEquals("Dole", u.getLastName()));
    }
}