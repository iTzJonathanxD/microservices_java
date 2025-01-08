package com.uleam.microservice_tipoPago.security.modelos.User;

public class UserResponse {
    private String message;

    public UserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
