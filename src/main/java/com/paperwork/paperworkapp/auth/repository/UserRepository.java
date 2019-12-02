package com.paperwork.paperworkapp.auth.repository;

import com.paperwork.paperworkapp.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
