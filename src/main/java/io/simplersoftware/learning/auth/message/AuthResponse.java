package io.simplersoftware.learning.auth.message;

import java.io.Serializable;

public class AuthResponse implements Serializable {


    private static final long serialVersionUID = -2370502376508339440L;
    private int status;
    private String message;
    private Object result;

    public AuthResponse(int status, String message, Object result){
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public AuthResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}