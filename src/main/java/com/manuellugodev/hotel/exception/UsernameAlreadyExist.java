package com.manuellugodev.hotel.exception;

public class UsernameAlreadyExist extends ConflictException{

    public UsernameAlreadyExist(String message) {
        super(message);
    }

    public UsernameAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameAlreadyExist(Throwable cause) {
        super(cause);
    }
}
