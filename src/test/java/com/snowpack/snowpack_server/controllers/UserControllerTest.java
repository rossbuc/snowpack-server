package com.snowpack.snowpack_server.controllers;

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

import static org.mockito.Mockito.when;
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
}
