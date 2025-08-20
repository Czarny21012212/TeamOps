package com.example.Backend.config;

public class WebSocketResponse<T> {
    private int status;
    private String message;
    private T data;

    public WebSocketResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
