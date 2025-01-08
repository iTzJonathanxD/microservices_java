package com.uleam.microservice_tipoPago.security.modelos.User;

public class UserRequest {
    public Integer id;
    private String firstname;

    public UserRequest(Integer id, String firstname) {
        this.id = id;
        this.firstname = firstname;
    }

    // Getters
    public String getFirstname() {
        return firstname;
    }
}
