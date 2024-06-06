package com.snowpack.snowpack_server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowpack.snowpack_server.models.User;
import com.snowpack.snowpack_server.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldHaveUsers() throws Exception {

        List<User> users = Arrays.asList(
                new User("1", "User One", "user1@example.com"),
                new User("2", "User Two", "user2@example.com"),
                new User("3", "User Three", "user3@example.com")
        );

        when(userRepository.findAll()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].username", is("1")))
                .andExpect(jsonPath("$[1].username", is("2")))
                .andExpect(jsonPath("$[2].username", is("3")));
    }

    @Test
    void getUserById() throws Exception {
        User user = new User("1", "User One", "user1@example.com");

        // Mocking repository to return Optional.of(user) when findById is called with "1"
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Performing GET request to /users/1
        mvc.perform(MockMvcRequestBuilders.get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expecting HTTP 200 OK
                .andExpect(jsonPath("$.id", is(0))) // Expecting JSON object with "id" equal to "1"
                .andExpect(jsonPath("$.username", is("1"))) // Expecting JSON object with "name" equal to "User One"
                .andExpect(jsonPath("$.email", is("user1@example.com"))); // Expecting JSON object with "email" equal to "user1@example.com"
    }

    @Test
    void canAddUser() throws Exception {
        User user = new User("username", "psssss", "somerandomemail@mail.com");

        String userJson = objectMapper.writeValueAsString(user);

        when(userRepository.save(any(User.class))).thenReturn(user);

        mvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void canUpdateUser() throws Exception {
        User existingUser = new User("1", "John Doe", "john.doe@example.com");
        User updatedUser = new User("updated name", "Updated Name", "updated.email@example.com");

        // Convert updatedUser object to JSON
        String updatedUserJson = objectMapper.writeValueAsString(updatedUser);

        // Mock repository findById and save methods
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Perform PUT request to /users/1 with updatedUserJson as request body
        mvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk()) // Expecting HTTP 200 OK
                .andExpect(jsonPath("$.id", is(0))) // Expecting JSON object with "id" equal to "1"
                .andExpect(jsonPath("$.username", is("updated name"))) // Expecting JSON object with "name" equal to "Updated Name"
                .andExpect(jsonPath("$.email", is("updated.email@example.com"))); // Expecting JSON object with "email" equal to "updated.email@example.com"
    }
}
