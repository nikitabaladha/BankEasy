package com.bankeasy.bankeasy.entities;

import java.util.UUID;

public class SignupResponse {
	
    private boolean hasError;
    private String message;
    private UUID userId; 

    public SignupResponse(boolean hasError, String message, UUID userId) {
        this.hasError = hasError;
        this.message = message;
        this.userId = userId;
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
    
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}