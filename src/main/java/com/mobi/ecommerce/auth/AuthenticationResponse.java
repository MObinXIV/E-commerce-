package com.mobi.ecommerce.auth;



public class AuthenticationResponse {

    private  String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse() {
    }

    public void setToken(String token) { this.token = token; }

    public String getToken() {
        return token;
    }
}
