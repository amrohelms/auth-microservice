package com.paperwork.paperworkapp.auth.service;

import com.paperwork.paperworkapp.auth.model.Role;
import com.paperwork.paperworkapp.auth.model.User;
import com.paperwork.paperworkapp.auth.repository.RoleRepository;
import com.paperwork.paperworkapp.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void save(String username, String password){

        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        User user = new User(username, password, roles);
        userRepository.save(user);

    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByEmail(username);
    }
}
