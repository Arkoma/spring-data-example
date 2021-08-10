package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService underTest;

    @Mock
    UserRepository mockUserRepository;

    @Test

    public void saveUser() {
        User testUser = new User("Mickey", "Mouse", null);
        when(mockUserRepository.save(any(User.class))).thenReturn(testUser);
        User result = underTest.saveUser(null, null, null);
        verify(mockUserRepository).save(any(User.class));
        assertEquals("Mickey", result.getFirstName());
        assertEquals("Mouse", result.getLastName());
    }
}