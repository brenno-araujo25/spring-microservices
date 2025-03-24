package com.microservices.user.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.user.dtos.UserDTO;
import com.microservices.user.models.User;
import com.microservices.user.services.UserService;

import java.util.List;
import java.util.UUID;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDTO = new UserDTO(
            "Test User",
            "test@example.com"
        );

        user = new User(
            UUID.randomUUID(),
            userDTO.getName(),
            userDTO.getEmail()
        );
    }

    @Test
    public void shouldCreateUser() throws Exception {
        when(userService.save(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(userDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].email").value(user.getEmail()))
            .andExpect(jsonPath("$[0].name").value(user.getName()));
    }

    @Test
    public void shouldReturnUserByEmail() throws Exception {
        when(userService.findByEmail(Mockito.anyString())).thenReturn(user);

        mockMvc.perform(get("/users/email")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user.getEmail())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        when(userService.findById(Mockito.any(UUID.class))).thenReturn(user);

        mockMvc.perform(get("/users/id")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user.getUserId())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void shouldReturnIfUserExistsByEmail() throws Exception {
        when(userService.existsByEmail(Mockito.anyString())).thenReturn(true);

        mockMvc.perform(get("/users/exists")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user.getEmail())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void shouldReturnIfUserDoesNotExistByEmail() throws Exception {
        when(userService.existsByEmail(Mockito.anyString())).thenReturn(false);

        mockMvc.perform(get("/users/exists")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user.getEmail())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(false));
    }

}
