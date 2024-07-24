

package com.bankeasy.bankeasy.entities;

public class SignupResponse {
    private boolean hasError;
    private String message;

    public SignupResponse(boolean hasError, String message) {
        this.hasError = hasError;
        this.message = message;
    }

    // Getters and setters
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
}
