package com.snowpack.snowpack_server.models;

import jakarta.persistence.*;

import java.awt.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "location_xCoordinate")
    private double xCoordinate;
    @Column(name = "location_yCoordinate")
    private double yCoordinate;
    @Column(name = "description")
    private String description;
    @Column(name = "elevation")
    private int elevation;
    @Column(name = "aspect")
    private Aspect aspect;
    @Column(name = "temperature")
    private int temperature;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(double xCoordinate, double yCoordinate, String description, int elevation, Aspect aspect, int temperature, User user) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.description = description;
        this.elevation = elevation;
        this.aspect = aspect;
        this.temperature = temperature;
        this.user = user;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public Aspect getAspect() {
        return aspect;
    }

    public void setAspect(Aspect aspect) {
        this.aspect = aspect;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
