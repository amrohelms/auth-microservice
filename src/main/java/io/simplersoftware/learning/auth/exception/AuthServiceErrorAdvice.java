package io.simplersoftware.learning.auth.exception;


import com.auth0.jwt.exceptions.JWTVerificationException;
import io.simplersoftware.learning.auth.message.AuthResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.hibernate.exception.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.*;


@ControllerAdvice
public class AuthServiceErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { JWTVerificationException.class, AuthenticationException.class, AccessDeniedException.class })
    public ResponseEntity<Object> handle(Exception exception) {

        AuthResponse authError = new AuthResponse(UNAUTHORIZED.value(), "You are unauthorised");
        return buildResponseEntity(authError);
    }


    @ExceptionHandler(value = { ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintException(ConstraintViolationException exception) {

        AuthResponse authError = new AuthResponse(BAD_REQUEST.value(), "Email address already exists");
        return buildResponseEntity(authError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> message = exception.getBindingResult().getFieldErrors().stream().map(fieldError->fieldError.getDefaultMessage()).collect(Collectors.toList());
        AuthResponse authError = new AuthResponse(BAD_REQUEST.value(), message.toString());
        return buildResponseEntity(authError);
    }

    private ResponseEntity<Object> buildResponseEntity(AuthResponse apiError) {
        return new ResponseEntity(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }

}
