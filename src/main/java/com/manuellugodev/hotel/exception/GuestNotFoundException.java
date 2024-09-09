package com.manuellugodev.hotel.exception;

public class GuestNotFoundException extends NotFoundException {
    public GuestNotFoundException(String message) {
        super(message);
    }
}