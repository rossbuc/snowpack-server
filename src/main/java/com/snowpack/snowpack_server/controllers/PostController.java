package com.snowpack.snowpack_server.controllers;

import com.snowpack.snowpack_server.models.Post;
import com.snowpack.snowpack_server.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping(value = "/posts")
    public ResponseEntity<List<Post>> getPosts(@RequestParam(name="recent", required = false)String recent) {
        if (recent != null) {
            return new ResponseEntity<>(postRepository.findAllSortByDateTime(), HttpStatus.OK);
        }
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<Optional<Post>> getPostByID(@PathVariable Long id) {
        return new ResponseEntity<Optional<Post>>(postRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping("/posts/new")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        postRepository.save(post);
        return ResponseEntity.ok().body(post);
    }
}
