package com.group_component.master_gateway.response;

import com.group_component.master_gateway.response.dto.SanitizedUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoginResponse {
    private String token;
    private SanitizedUser user;

    public LoginResponse(String token, SanitizedUser user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public SanitizedUser getUser() {
        return this.user;
    }

    public static ResponseEntity<LoginResponse> create(String token, SanitizedUser user) {
        return new ResponseEntity<>(new LoginResponse(token, user), HttpStatus.OK);
    }
}
