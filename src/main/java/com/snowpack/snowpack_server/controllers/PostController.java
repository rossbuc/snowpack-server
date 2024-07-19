package com.snowpack.snowpack_server.controllers;

import com.snowpack.snowpack_server.models.Aspect;
import com.snowpack.snowpack_server.models.Post;
import com.snowpack.snowpack_server.repositories.PostRepository;
import com.snowpack.snowpack_server.specifications.PostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
            @RequestParam(name="sortBy", required = false) String sortBy,
            @RequestParam(name="elevation", required = false) String elevation,
            @RequestParam(name="aspect", required = false) String aspect,
            @RequestParam(name="temperature", required = false) String temperature
    ) throws NumberFormatException, IllegalArgumentException {
        Integer elevationInt = elevation != null ? Integer.parseInt(elevation) : null;
        Integer temperatureInt = temperature != null ? Integer.parseInt(temperature) : null;
        Aspect parsedAspect = aspect != null ? Aspect.valueOf(aspect) : null;

        Specification<Post> specification = Specification.where(PostSpecification.hasTemperatureGreaterThanEqual(temperatureInt))
                .and(PostSpecification.hasElevationGreaterThanEqual(elevationInt))
                .and(PostSpecification.hasAspect(parsedAspect))
                .and(PostSpecification.isSortedByDateTimeDesc(sortBy));

        List<Post> posts = postRepository.findAll(specification);
        return new ResponseEntity<>(posts, HttpStatus.OK);
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
