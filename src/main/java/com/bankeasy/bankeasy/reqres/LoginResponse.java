package com.bankeasy.bankeasy.reqres;


public class LoginResponse {
    private boolean hasError;
    private String message;
    private String token; 

    public LoginResponse(boolean hasError, String message, String token) {
        this.hasError = hasError;
        this.message = message;
        this.token = token;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}