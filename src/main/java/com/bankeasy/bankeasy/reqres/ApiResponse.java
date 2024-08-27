package com.bankeasy.bankeasy.reqres;

public class ApiResponse<T> {

    private boolean hasError;
    private String message;
    private T data;

    public ApiResponse(boolean hasError, String message, T data) {
        this.hasError = hasError;
        this.message = message;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

