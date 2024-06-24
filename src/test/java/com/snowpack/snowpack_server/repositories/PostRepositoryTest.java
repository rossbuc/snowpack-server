package com.snowpack.snowpack_server.repositories;

import com.snowpack.snowpack_server.models.Aspect;
import com.snowpack.snowpack_server.models.Post;
import com.snowpack.snowpack_server.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void testFindPostByElevationGreaterThanEqual() {
        User user = new User("username", "psssss", "somerandomemail@mail.com");

        Post post1 = new Post(34.45, 56.902, LocalDateTime.now(), "title1", "description1", 3490, Aspect.NE, 4, user);
        Post post2 = new Post(394.6, 1.9, LocalDateTime.of(2023, 6, 20, 12, 1, 1), "title2", "description2", 340, Aspect.E, 14, user);
        Post post3 = new Post(-4.456, 53.1, LocalDateTime.of(2022, 6, 20, 12, 1, 1), "title3", "description3", 9490, Aspect.N, -40, user);

        userRepository.save(user);
        postRepository.saveAll(List.of(post1, post2, post3));

        List<Post> posts = postRepository.findPostByElevationGreaterThanEqual(350);

        assertThat(posts).hasSize(2);
        assertThat(posts).extracting("title").containsExactlyInAnyOrder("title1", "title3");
    }

}
