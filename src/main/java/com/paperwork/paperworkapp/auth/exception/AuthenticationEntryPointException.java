//package com.paperwork.paperworkapp.auth.exception;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.Serializable;
//
//@Component
//public class AuthenticationEntryPointException implements AuthenticationEntryPoint, Serializable {
//
//    private static final long serialVersionUID = -3478994434893333983L;
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//                         AuthenticationException authException) throws IOException {
//
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized!");
//    }
//}