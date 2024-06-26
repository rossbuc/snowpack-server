package com.snowpack.snowpack_server.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;
    User follower;
    Post post;

    @BeforeEach
    void setup() {
        user = new User("mockuser", "mockuserPSSword", "mock@user.com");
        follower = new User("follower", "mockuserPSSword", "follower@user.com");
        post = new Post(45.567, 108.34555, LocalDateTime.now(), "title", "some ski stuff shoowsh swoosh", 5679, Aspect.E, -1, user);
    }

    @Test
    void getUsername() {
        assertEquals("mockuser", user.getUsername());
    }

    @Test
    void setUsername() {
        user.setUsername("newName");
        assertEquals("newName", user.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("mockuserPSSword", user.getPassword());
    }

    @Test
    void setPassword() {
        user.setPassword("newpword");
        assertEquals("newpword", user.getPassword());
    }

    @Test
    void getEmail() {
        assertEquals("mock@user.com", user.getEmail());
    }

    @Test
    void setEmail() {
        user.setEmail("newEmail");
        assertEquals("newEmail", user.getEmail());
    }

    @Test
    void getPosts() {
        assertNotNull(user.getPosts());
    }

    @Test
    void setPosts() {
        List<Post> newPosts = user.getPosts();
        newPosts.add(post);

        assertEquals(1, user.getPosts().size());
    }

    @Test
    void getId() {
        assertNotNull(user.getId());
    }

    @Test
    void hasFollowers() {
        assertNotNull(user.getFollowers());
    }

    @Test
    void canAddFollower() {
        user.getFollowers().add(follower);
        assertEquals(1, user.getFollowers().size());
    }

    @Test
    void hasFollowing() {
        assertNotNull(user.getFollowing());
    }

    @Test
    void canFollow() {
        user.getFollowing().add(follower);
        assertEquals(1, user.getFollowing().size());
    }
}