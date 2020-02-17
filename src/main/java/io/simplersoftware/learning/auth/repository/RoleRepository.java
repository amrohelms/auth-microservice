package io.simplersoftware.learning.auth.repository;


import io.simplersoftware.learning.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
