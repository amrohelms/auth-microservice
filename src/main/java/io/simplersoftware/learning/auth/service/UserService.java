package io.simplersoftware.learning.auth.service;

import io.simplersoftware.learning.auth.model.User;

public interface UserService {

    void save(String username, String password);

    User findByUsername(String username);

}
