package com.snowpack.snowpack_server.controllers;

import com.snowpack.snowpack_server.models.User;
import com.snowpack.snowpack_server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value = "/users/{id}", produces="application/json")
    public Optional<User> updatesUserInfo(@PathVariable Long id, @RequestBody User newUser) {
        return Optional.of(userRepository.findById(id).map(user -> {
            user.setEmail(newUser.getEmail());
            user.setUsername((newUser.getUsername()));
            user.setPassword(newUser.getPassword());
            user.setPosts(newUser.getPosts());
            return userRepository.save(user);
        }).orElseGet(() -> {
            System.out.println("User not found ");
            throw new RuntimeException();
        }));
    }
}
