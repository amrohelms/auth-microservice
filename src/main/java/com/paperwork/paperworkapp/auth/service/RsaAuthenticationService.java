package com.paperwork.paperworkapp.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.paperwork.paperworkapp.auth.util.ServerKeyStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;
import java.util.Date;

@Service
public class RsaAuthenticationService implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ServerKeyStore keyStore;
    @Value("${APP.SECURITY.JWT.TOKEN.ISSUER}")
    private String issuer;
    @Value("${APP.SECURITY.JWT.TOKEN.TIME-TO-LIVE}")
    private long timeToLive;


    @Override
    public String authenticate(String username, String password) {

        String token = null;


            // Here we ask the authentication manager (a public interface provided by spring Security) to authenticate the username/password
            // of the user. BEHIND THE SCENE -- the auth-manager will call the loadUserByUsername(email) then hash and compare passwords
            // if success then it'll also create the authentication context for the user.
            // We're passing an instance of Authentication using the username and password. BEHIND THE SCENE -- this will create the Principal
            // and the Credentials instances which will be available to access anywhere in your application. Principal specifically is important
            // as it holds all the user details and roles/authorities.
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            // After the user is authenticated we need to create a token and send it back so the user can include it in all subsequent requests
            // to access protected resources. Using a token eliminates the need to send credentials with each request, reducing the chance of
            // theft. We are making use of the auth0 library to generate the token, but before we can generate a token we need to retrieve the
            // keys and configure the algorithm used to generate the token. Keys are handled by the RsaEncryption class, here we are calling a
            // method on that class to return the private key which is needed to create a signature from the token's header and payload.

            // Create a new RSA256 algorithm instance with our private key
            Algorithm algorithmRS = Algorithm.RSA256(null, (RSAPrivateKey) keyStore.getKeyPairFromKeyStore().getPrivate());

            // Create the token creator instance and define your claims. Claims are the data you want to include in the payload part of the token
            // In many cases you'll need to include something that is unique to the user so you can perform actions specific to the user.
            // In most cases you'll need to include some standard data such as the expiration date of the token, issuer, subject, audience, etc.
            token = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(username)
                    .withClaim("roles", auth.getAuthorities().toString())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(new Date().getTime() + timeToLive))
                    .sign(algorithmRS);

        return token;
    }

    @Override
    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(keyStore.getKeyPairFromKeyStore().getPublic().getEncoded());
    }

}
