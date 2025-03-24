package com.microservices.user.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.microservices.user.exceptions.UserAlreadyExistsException;
import com.microservices.user.exceptions.UserNotFoundException;
import com.microservices.user.models.User;
import com.microservices.user.producers.UserProducer;
import com.microservices.user.repositories.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProducer userProducer;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(java.util.UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setName("Test User");
    }

    @Test
    public void shouldSaveUser() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        var savedUser = userService.save(user);

        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getName(), savedUser.getName());
    }

    @Test
    public void shouldThrowUserAlreadyExistsException() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.save(user));
    }

    @Test
    public void shouldFindAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        var users = userService.findAll();

        assertEquals(1, users.size());
        assertEquals(user.getEmail(), users.get(0).getEmail());
        assertEquals(user.getName(), users.get(0).getName());
    }

    @Test
    public void shouldFindByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.of(user));

        var foundUser = userService.findByEmail(user.getEmail());

        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    public void shouldThrowUserNotFoundException() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByEmail(user.getEmail()));
    }

    @Test
    public void shouldFindById() {
        when(userRepository.findById(user.getUserId())).thenReturn(java.util.Optional.of(user));

        var foundUser = userService.findById(user.getUserId());

        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    public void shouldThrowUserNotFoundExceptionById() {
        when(userRepository.findById(user.getUserId())).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(user.getUserId()));
    }

    @Test
    public void shouldExistsByEmail() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        var exists = userService.existsByEmail(user.getEmail());

        assertEquals(true, exists);
    }
    
}
