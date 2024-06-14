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

import java.util.Random;

//Comment out @Component to avoid running the data loader each time
@Profile("!test")
//@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public DataLoader() {
    }

    public void run(ApplicationArguments args) {
        // Create mock users
        User user1 = new User("rossbuc", "123456", "rossbuchan@duttycoding.sick");
        User user2 = new User("hamishrendick", "23456h", "hamish@hdog.net");
        User user3 = new User("sickskir", "4856ht", "shredsauce.shred");
        User user4 = new User("janedoe", "password", "janedoe@example.com");
        User user5 = new User("johndoe", "password1", "johndoe@example.com");

        // Add users to user repository
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        // Create random posts
        Random random = new Random();
        Aspect[] aspects = Aspect.values();
        String[] descriptions = {
                "Amazing snow conditions today!",
                "Watch out for icy patches.",
                "Perfect weather for skiing.",
                "Fresh powder, it's a dream!",
                "Visibility is low, be careful.",
                "Snow is melting fast, slushy conditions.",
                "Great day on the slopes!",
                "Windy and cold, dress warm!",
                "Early morning runs are the best!",
                "Afternoon sun makes for perfect conditions."
        };

        for (int i = 0; i < 200; i++) {
            double latitude = -90 + (90 - (-90)) * random.nextDouble();
            double longitude = -180 + (180 - (-180)) * random.nextDouble();
            String description = descriptions[random.nextInt(descriptions.length)] + " Post number " + (i + 1);
            int elevation = random.nextInt(8000);
            Aspect aspect = aspects[random.nextInt(aspects.length)];
            int dangerLevel = random.nextInt(5);
            User user = userRepository.findAll().get(random.nextInt((int) userRepository.count()));

            Post post = new Post(latitude, longitude, description, elevation, aspect, dangerLevel, user);
            postRepository.save(post);
        }
    }
}
