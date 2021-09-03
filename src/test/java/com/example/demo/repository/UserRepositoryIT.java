package com.example.demo.repository;

import com.example.demo.model.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
class UserRepositoryIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserRepository underTest;

    @Test
    public void userRepositoryIsABean() {
        assertTrue(applicationContext.containsBean("userRepository"));
    }

    @Test
    @DatabaseSetup("/dataset/users.xml")
    void testFindByFirstName() {
        Long id = 10L;
        String firstName = "George";
        String lastName = "Washington";
        String email = "firstpres@example.com";
        Optional<User> user = underTest.findByFirstName(firstName);
        assertAll(() -> {
            assertTrue(user.isPresent());
            assertEquals(id, user.get().getId());
            assertEquals(firstName, user.get().getFirstName());
            assertEquals(lastName, user.get().getLastName());
            assertEquals(email, user.get().getEmail());
        });
    }
}