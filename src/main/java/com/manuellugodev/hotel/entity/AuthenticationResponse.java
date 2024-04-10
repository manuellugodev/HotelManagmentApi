package com.manuellugodev.hotel.entity;

public class AuthenticationResponse {


    private final String token;
    private final String errorMessage;

    public AuthenticationResponse(String jwt, String errorMessage) {
        this.token = jwt;
        this.errorMessage = errorMessage;
    }

    public AuthenticationResponse(String token) {
        this.token = token;
        this.errorMessage = null;
    }

    public String getToken() {
        return token;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
