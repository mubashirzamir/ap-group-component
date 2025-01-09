package com.group_component.master_gateway.model;

public class AuthenticatedUser {
    private String email;
    private String password;

    public AuthenticatedUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
