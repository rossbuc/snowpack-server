package com.snowpack.snowpack_server.components;

import com.snowpack.snowpack_server.models.User;
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
    }
}
