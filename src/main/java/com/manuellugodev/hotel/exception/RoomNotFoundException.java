package com.manuellugodev.hotel.exception;

// GuestNotFoundException.java
public class RoomNotFoundException extends NotFoundException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}