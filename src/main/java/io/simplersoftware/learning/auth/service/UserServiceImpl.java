package io.simplersoftware.learning.auth.service;

import io.simplersoftware.learning.auth.model.Role;
import io.simplersoftware.learning.auth.model.User;
import io.simplersoftware.learning.auth.repository.RoleRepository;
import io.simplersoftware.learning.auth.repository.UserRepository;
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
