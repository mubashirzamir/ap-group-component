package com.group_component.master_gateway.request;

public class ChangePasswordRequest {
    private final String currentPassword;
    private final String newPassword;
    private final String confirm;

    public ChangePasswordRequest(String currentPassword, String newPassword, String confirm) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirm = confirm;
    }

    public String getCurrentPassword() {
        return this.currentPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public String getConfirm() {
        return this.confirm;
    }
}