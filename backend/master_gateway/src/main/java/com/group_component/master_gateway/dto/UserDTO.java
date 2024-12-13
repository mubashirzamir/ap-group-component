package com.group_component.master_gateway.dto;

import java.util.HashMap;
import java.util.Map;

public class UserDTO {
    private String email;
    private String password;
    private String confirm;

    public UserDTO() {
    }

    public UserDTO(String email, String password, String confirm) {
        this.email = email;
        this.password = password;
        this.confirm = confirm;
    }

    public UserDTO setEmail(String email) {
        this.email = email;

        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public UserDTO setPassword(String password) {
        this.password = password;

        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public UserDTO setConfirm(String confirm) {
        this.confirm = confirm;

        return this;
    }

    public String getConfirm() {
        return this.confirm;
    }

    public Map<String, String> toLoginResponse() {
        Map<String, String> response = new HashMap<>();
        response.put("email", this.email);

        return response;
    }
}


