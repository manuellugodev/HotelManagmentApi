package com.manuellugodev.hotel.exception;

import org.springframework.security.access.AccessDeniedException;

public class AccessDeniedResourceException extends RuntimeException {

    public AccessDeniedResourceException(String s) {
        super(s);
    }
}
