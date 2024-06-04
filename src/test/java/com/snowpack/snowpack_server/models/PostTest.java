package com.snowpack.snowpack_server.models;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    User user;
    Post post;

    @BeforeEach
    public void setUp() {
        user = new User("mockuser", "mockuserPSSword", "mock@user.com");
        post = new Post(45.567, 108.34555, "some ski stuff shoowsh swoosh", 5679, Aspect.E, -1, user);
    }
    

    @Test
    void getxCoordinate() {
        assertEquals(45.567, post.getxCoordinate());
    }

    @Test
    void setxCoordinate() {
        post.setxCoordinate(108.334);
        assertEquals(108.334, post.getxCoordinate());
    }

    @Test
    void getyCoordinate() {
        assertEquals(108.34555, post.getyCoordinate());
    }

    @Test
    void setyCoordinate() {
        post.setyCoordinate(108.334);
        assertEquals(108.334, post.getyCoordinate());
    }

    @Test
    void getId() {
        assertNotNull(post.getId());
    }

    @Test
    void getDescription() {
        assertEquals("some ski stuff shoowsh swoosh", post.getDescription());
    }

    @Test
    void setDescription() {
        post.setDescription("new description");
        assertEquals("new description", post.getDescription());
    }

    @Test
    void getElevation() {
        assertEquals(5679, post.getElevation());
    }

    @Test
    void setElevation() {
        post.setElevation(340);
        assertEquals(340, post.getElevation());
    }

    @Test
    void getAspect() {
        assertEquals(Aspect.E, post.getAspect());
    }

    @Test
    void setAspect() {
        post.setAspect(Aspect.NE);
        assertEquals(Aspect.NE, post.getAspect());
    }

    @Test
    void getTemperature() {
        assertEquals(-1, post.getTemperature());
    }

    @Test
    void setTemperature() {
        post.setTemperature(-10);
        assertEquals(-10, post.getTemperature());
    }

    @Test
    void getUser() {
        assertEquals(user, post.getUser());
    }
}