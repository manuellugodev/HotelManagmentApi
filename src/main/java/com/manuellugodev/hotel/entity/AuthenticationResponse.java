package com.manuellugodev.hotel.entity;

public class AuthenticationResponse {


    private final String accessToken;

    private final String refreshToken;
    private final String errorMessage;

    private final int guestId;

    public AuthenticationResponse(String accessToken,String refreshToken, String errorMessage, int guestId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.errorMessage = errorMessage;
        this.guestId = guestId;
    }

    public AuthenticationResponse(String accessToken,String refreshToken, int guestId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.guestId = guestId;
        this.errorMessage = null;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getGuestId(){return guestId;}

    public String getRefreshToken() {
        return refreshToken;
    }
}
