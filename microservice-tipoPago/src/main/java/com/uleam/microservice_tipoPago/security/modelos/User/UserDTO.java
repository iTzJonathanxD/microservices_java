package com.uleam.microservice_tipoPago.security.modelos.User;

public class UserDTO {
    private Integer id;
    private String username;
    private String firstname;

    public UserDTO(Integer id, String username, String firstname) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
