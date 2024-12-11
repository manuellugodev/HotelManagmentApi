package com.manuellugodev.hotel.entity;

public class AuthenticationResponse {


    private final String token;
    private final String errorMessage;

    private final int guestId;

    public AuthenticationResponse(String jwt, String errorMessage, int guestId) {
        this.token = jwt;
        this.errorMessage = errorMessage;
        this.guestId = guestId;
    }

    public AuthenticationResponse(String token, int guestId) {
        this.token = token;
        this.guestId = guestId;
        this.errorMessage = null;
    }

    public String getToken() {
        return token;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getGuestId(){return guestId;}
}
