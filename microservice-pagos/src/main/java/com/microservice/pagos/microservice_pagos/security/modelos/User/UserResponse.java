package com.microservice.pagos.microservice_pagos.security.modelos.User;

public class UserResponse {
    private String message;

    public UserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
