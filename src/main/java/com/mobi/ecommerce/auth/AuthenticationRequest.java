package com.mobi.ecommerce.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticationRequest {
    @Email(message = "email is not formatted ")
    @NotEmpty(message = "email is required ")
    private String email;
    @NotEmpty(message = "password is required")
    @Size(min = 8,message = "password is at least 8 characters ")
    private String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
