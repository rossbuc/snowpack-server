package com.snowpack.snowpack_server.repositories;

import com.snowpack.snowpack_server.models.Aspect;
import com.snowpack.snowpack_server.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByDateTimeDesc();

    List<Post> findPostByElevationGreaterThanEqual(int elevation);

    List<Post> findPostByAspect(Aspect aspect);

    List<Post> findPostByTemperatureGreaterThanEqual(int temperature);
}
