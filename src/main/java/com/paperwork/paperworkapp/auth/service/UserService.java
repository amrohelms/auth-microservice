package com.paperwork.paperworkapp.auth.service;

import com.paperwork.paperworkapp.auth.model.User;

public interface UserService {

    void save(String username, String password);

    User findByUsername(String username);

}
