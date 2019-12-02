package com.paperwork.paperworkapp.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;


public class AuthRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private ServerKeyStore keyStore;
    @Value("${APP.SECURITY.JWT.TOKEN.ISSUER}")
    private String issuer;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String token = getJwtFromRequest(request);

            RSAPublicKey publicKey = (RSAPublicKey) keyStore.getKeyPairFromKeyStore().getPublic();
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT jwt = verifier.verify(token);

            createAuthenticationContext(jwt.getSubject(), request);
        }
        catch(Exception exc) { }
        chain.doFilter(request, response);
    }


    private void createAuthenticationContext(String username, HttpServletRequest request) {

        UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }


    public String getJwtFromRequest(HttpServletRequest request) {

        System.out.println("\n"+request.getAuthType()+"\n");
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        else{
          return null;
        }
    }

}
