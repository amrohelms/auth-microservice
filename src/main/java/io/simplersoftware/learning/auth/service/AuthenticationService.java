package io.simplersoftware.learning.auth.service;


public interface AuthenticationService {

    String authenticate(String username, String password) ;

    String getPublicKey();
}
