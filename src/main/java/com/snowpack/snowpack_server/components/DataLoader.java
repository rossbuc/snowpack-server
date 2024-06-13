package com.snowpack.snowpack_server.components;

import com.snowpack.snowpack_server.models.Aspect;
import com.snowpack.snowpack_server.models.Post;
import com.snowpack.snowpack_server.models.User;
import com.snowpack.snowpack_server.repositories.PostRepository;
import com.snowpack.snowpack_server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


//Comment out @Component to avoid running the DataLoader
@Profile("!test")
//@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public DataLoader() {
    }

    public void run(ApplicationArguments arga) {
//        Create mock users
        User user1 = new User("rossbuc", "123456", "rossbuchan@duttycoding.sick");
        User user2 = new User("hamishrendick", "23456h", "hamish@hdog.net");
        User user3 = new User("sickskir", "4856ht", "shredsauce.shred");

//        Add users to user repository
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Post post1 = new Post(45.555, -0.999, "some silly descriptions yea boi", 6790, Aspect.NE, 20, user1);
        Post post2 = new Post(5.555, -10.999, "some silly even more silly descriptions yea boi", 690, Aspect.N, 0, user1);
        Post post3 = new Post(40.555, 40.999, "some silly descriptions in a different place but by the same dood yea boi", 6790, Aspect.S, 3, user1);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
    }
}
