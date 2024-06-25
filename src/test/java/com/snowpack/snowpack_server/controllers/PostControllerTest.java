package com.snowpack.snowpack_server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowpack.snowpack_server.models.Aspect;
import com.snowpack.snowpack_server.models.Post;
import com.snowpack.snowpack_server.models.User;
import com.snowpack.snowpack_server.repositories.PostRepository;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void canFilterByElevation() throws Exception {
        User user = new User("username", "psssss", "somerandomemail@mail.com");

        Post post1 = new Post(34.45, 56.902, LocalDateTime.now(), "title1", "description1", 3490, Aspect.NE, 4, user);
        Post post2 = new Post(394.6, 1.9, LocalDateTime.of(2023, 6, 20, 12, 1, 1), "title2", "description2", 340, Aspect.E, 14, user);
        Post post3 = new Post(-4.456, 53.1, LocalDateTime.of(2022, 6, 20, 12, 1, 1), "title3", "description3", 9490, Aspect.N, -40, user);

        // Test case with elevation >= 350
        List<Post> postsAbove350 = Arrays.asList(post1, post3);
        when(postRepository.findPostByElevationGreaterThanEqual(350)).thenReturn(postsAbove350);

        mvc.perform(MockMvcRequestBuilders.get("/posts")
                        .param("elevation", "350")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("title1")))
                .andExpect(jsonPath("$[0].elevation", is(3490)))
                .andExpect(jsonPath("$[1].title", is("title3")))
                .andExpect(jsonPath("$[1].elevation", is(9490)));

        // Test case with elevation >= 9500 (should return empty)
        List<Post> noPostsAbove9500 = Arrays.asList();
        when(postRepository.findPostByElevationGreaterThanEqual(9500)).thenReturn(noPostsAbove9500);

        mvc.perform(MockMvcRequestBuilders.get("/posts")
                        .param("elevation", "9500")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
