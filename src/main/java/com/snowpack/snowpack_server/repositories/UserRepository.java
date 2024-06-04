package com.snowpack.snowpack_server.repositories;

import com.snowpack.snowpack_server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
