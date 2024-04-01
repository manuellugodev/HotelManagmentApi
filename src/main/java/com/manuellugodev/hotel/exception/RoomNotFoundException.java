package com.manuellugodev.hotel.exception;

// GuestNotFoundException.java
public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}