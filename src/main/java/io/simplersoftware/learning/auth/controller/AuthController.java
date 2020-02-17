package io.simplersoftware.learning.auth.controller;


import io.simplersoftware.learning.auth.message.AuthRequest;
import io.simplersoftware.learning.auth.message.AuthResponse;
import io.simplersoftware.learning.auth.message.JwtResponse;
import io.simplersoftware.learning.auth.service.AuthenticationService;
import io.simplersoftware.learning.auth.service.UserService;
import io.simplersoftware.learning.auth.util.AuthRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthRequestFilter authRequestFilter;


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest user) {

        String token = authenticationService.authenticate(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(new AuthResponse(HttpStatus.OK.value(), "Token is generated successfully", new JwtResponse(token)));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequest user) {

        userService.save(user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(new AuthResponse(HttpStatus.OK.value(), "User registered successfully"));
    }

    @GetMapping("/publicKey")
    public ResponseEntity<?> getPublicKey(){

        return ResponseEntity.ok(new AuthResponse(HttpStatus.OK.value(), "", authenticationService.getPublicKey()));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Principal user) {

        return ResponseEntity.ok(new AuthResponse(HttpStatus.OK.value(), "", user ));
    }

}
