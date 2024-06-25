package com.snowpack.snowpack_server.controllers;

import com.snowpack.snowpack_server.models.Post;
import com.snowpack.snowpack_server.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping(value = "/posts")
    public ResponseEntity<List<Post>> getPosts(
            @RequestParam(name="recent", required = false) String recent,
            @RequestParam(name="elevation", required = false) String elevation
    ) {
        if (recent != null) {
            System.out.println(postRepository.findAll());
//            List<Post> posts = postRepository.findAll();
//            sortByDateTimeDescending(posts);
//            System.out.println(posts);
            return new ResponseEntity<>(postRepository.findAllByOrderByDateTimeDesc(), HttpStatus.OK);
        }
        if (elevation != null) {
            int elevationInt = Integer.parseInt(elevation);
            return new ResponseEntity<>(postRepository.findPostByElevationGreaterThanEqual(elevationInt), HttpStatus.OK);
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

    public static void sortByDateTimeDescending(List<Post> objects) {
        objects.sort(new Comparator<Post>() {
            @Override
            public int compare(Post obj1, Post obj2) {
                // Sorting in descending order
                return obj2.getDateTime().compareTo(obj1.getDateTime());
            }
        });
    }
}
