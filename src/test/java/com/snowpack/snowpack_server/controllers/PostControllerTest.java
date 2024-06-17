package com.snowpack.snowpack_server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldHavePosts() throws Exception {

        User user = new User("username", "passst", "user@gmail.com");

        List<Post> posts = Arrays.asList(
                new Post(34.45, 56.902, LocalDateTime.now(), "title", "some desccription blaablaaablaaa", 3490, Aspect.NE, 4, user),
                new Post(394.6, 1.9, LocalDateTime.now(), "title", "some desccription blaablaaablaaa", 340, Aspect.E, 14, user),
                new Post(-4.456, 53.1, LocalDateTime.now(), "title", "some desccription blaablaaablaaa", 9490, Aspect.N, -40, user)
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

    @Test
    public void shouldGetPostById() throws Exception {
        User user = new User("username", "passst", "user@gmail.com");

        Post post = new Post(34.45, 56.902, LocalDateTime.now(), "title", "some desccription blaablaaablaaa", 3490, Aspect.NE, 4, user);


        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        mvc.perform(MockMvcRequestBuilders.get("/posts/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.xCoordinate", is(34.45)))
                .andExpect(jsonPath("$.yCoordinate", is(56.902)))
                .andExpect(jsonPath("$.description", is("some desccription blaablaaablaaa")))
                .andExpect(jsonPath("$.elevation", is(3490)))
                .andExpect(jsonPath("$.aspect", is("NE")))
                .andExpect(jsonPath("$.temperature", is(4)))
                .andExpect(jsonPath("$.user.username", is("username")))
                .andExpect(jsonPath("$.user.email", is("user@gmail.com")));

    }

    @Test
    public void shouldCreatePost() throws Exception {

        User user = new User("username", "psssss", "somerandomemail@mail.com");

        Post post = new Post(34.45, 56.902, LocalDateTime.now(), "title", "some desccription blaablaaablaaa", 3490, Aspect.NE, 4, user);

        String postJson = objectMapper.writeValueAsString(post);

        when(postRepository.save(any(Post.class))).thenReturn(post);

        mvc.perform(post("/posts/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isOk());

        verify(postRepository).save(any(Post.class));
    }

}