package com.paperwork.paperworkapp.auth.service;


public interface AuthenticationService {

    String authenticate(String username, String password) ;

    String getPublicKey();
}
