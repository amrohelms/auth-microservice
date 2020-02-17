package io.simplersoftware.learning.auth.message;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class AuthRequest implements Serializable {
    private static final long serialVersionUID = 7823968005589515993L;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Length(min= 8, message = "Password must be at least 8 characters")
    @NotBlank(message = "Password is mandatory")
    private String password;

    //need default constructor for JSON Parsing
    public AuthRequest() { }

    public AuthRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
