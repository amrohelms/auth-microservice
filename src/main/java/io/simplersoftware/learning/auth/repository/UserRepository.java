package io.simplersoftware.learning.auth.repository;

import io.simplersoftware.learning.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
