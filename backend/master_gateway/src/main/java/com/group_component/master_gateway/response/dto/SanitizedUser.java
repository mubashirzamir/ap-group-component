package com.group_component.master_gateway.response.dto;

import org.springframework.security.core.userdetails.UserDetails;

public class SanitizedUser {
    private String email;

    public SanitizedUser() {
    }

    public SanitizedUser setEmail(String email) {
        this.email = email;

        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public static SanitizedUser instance(UserDetails userDetails) {
        return new SanitizedUser()
                .setEmail(userDetails.getUsername());
    }
}
