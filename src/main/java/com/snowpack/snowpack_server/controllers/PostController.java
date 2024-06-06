package com.snowpack.snowpack_server.controllers;

import com.snowpack.snowpack_server.models.Post;
import com.snowpack.snowpack_server.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping(value = "/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<Post> getPostByID(@PathVariable Long id) {
        return new ResponseEntity<Post>(postRepository.getReferenceById(id), HttpStatus.OK);
    }
}
