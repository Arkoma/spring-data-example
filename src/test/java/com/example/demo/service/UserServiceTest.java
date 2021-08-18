package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService underTest;

    @Mock
    private UserRepository mockRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void saveUserCallsUserRepositorySaveMethod() {
        underTest.saveUser(null, null, null);
        verify(mockRepository).save(any(User.class));
    }

    @Test
    public void saveUserReturnsUser() {
        when(mockRepository.save(any(User.class))).thenReturn(new User(null, null, null));
        User result = underTest.saveUser(null, null, null);
        assertEquals("User", result.getClass().getSimpleName());
    }

    @Test
    public void saveUserPassesArgumentsIntoNewUser() {
        String firstName = "Ryan";
        String lastName = "Grillo";
        String email = "email@example.com";
        underTest.saveUser(firstName, lastName, email);
        verify(mockRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
    }
}
