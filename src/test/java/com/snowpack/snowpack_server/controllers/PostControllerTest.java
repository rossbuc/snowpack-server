package com.snowpack.snowpack_server.controllers;

import com.snowpack.snowpack_server.models.Aspect;
import com.snowpack.snowpack_server.models.Post;
import com.snowpack.snowpack_server.models.User;
import com.snowpack.snowpack_server.repositories.PostRepository;
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

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@ActiveProfiles("test")
class PostControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PostRepository postRepository;

    @Test
    public void shouldHavePosts() throws Exception {

        User user = new User("username", "passst", "user@gmail.com");

        List<Post> posts = Arrays.asList(
                new Post(34.45, 56.902, "some desccription blaablaaablaaa", 3490, Aspect.NE, 4, user),
                new Post(394.6, 1.9, "some desccription blaablaaablaaa", 340, Aspect.E, 14, user),
                new Post(-4.456, 53.1, "some desccription blaablaaablaaa", 9490, Aspect.N, -40, user)
        );

        when(postRepository.findAll()).thenReturn(posts);

        mvc.perform(MockMvcRequestBuilders.get("/posts")
                      .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].xCoordinate", is(34.45)))
                .andExpect(jsonPath("$[0].yCoordinate", is(56.902)))
                .andExpect(jsonPath("$[0].description", is("some desccription blaablaaablaaa")))
                .andExpect(jsonPath("$[0].elevation", is(3490)))
                .andExpect(jsonPath("$[0].aspect", is("NE")))
                .andExpect(jsonPath("$[0].temperature", is(4)))
                .andExpect(jsonPath("$[0].user.username", is("username")))
                .andExpect(jsonPath("$[0].user.email", is("user@gmail.com")));
    }

}