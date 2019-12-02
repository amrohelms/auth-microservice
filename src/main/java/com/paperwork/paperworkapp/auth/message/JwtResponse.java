package com.paperwork.paperworkapp.auth.message;

import java.io.Serializable;

public class JwtResponse implements Serializable {


    private static final long serialVersionUID = -6934579091554346834L;
    private final String token;

    public JwtResponse(String jwtToken) {
        token = jwtToken;
    }

    public String getToken() {
        return token;
    }
}
