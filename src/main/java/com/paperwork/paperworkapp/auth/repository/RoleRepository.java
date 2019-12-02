package com.paperwork.paperworkapp.auth.repository;


import com.paperwork.paperworkapp.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
