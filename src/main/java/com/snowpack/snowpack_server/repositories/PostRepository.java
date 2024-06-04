package com.snowpack.snowpack_server.repositories;

import com.snowpack.snowpack_server.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
