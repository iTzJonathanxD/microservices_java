package com.uleam.microservice_tipoPago.security.modelos;

public class RegisterRequest {
    private String username;
    private String password;
    private String firstname;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String firstname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
