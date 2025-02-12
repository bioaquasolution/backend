package com.thexbyte.bioaqua.utils;

public class ConfirmMailRequest {
    String email;
    String code;

    public ConfirmMailRequest() {
    }
    public ConfirmMailRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}