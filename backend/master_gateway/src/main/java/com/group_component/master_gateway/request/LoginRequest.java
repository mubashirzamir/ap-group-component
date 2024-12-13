package com.group_component.master_gateway.request;

public class LoginRequest {
    private final String email;
    private final String password;

    public LoginRequest(String email, String password, String confirm) {
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


