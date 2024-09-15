package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.AuthenticationResponse;
import com.manuellugodev.hotel.entity.ServerResponse;
import com.manuellugodev.hotel.exception.ConflictException;
import com.manuellugodev.hotel.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<ServerResponse> handleException(NotFoundException exception){

        ServerResponse response = new ServerResponse();

        response.setMesssage(exception.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimeStamp(System.currentTimeMillis());
        response.setErrorType(exception.getClass().getSimpleName());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler
    public ResponseEntity<ServerResponse> handleException(ConflictException exception){

        ServerResponse response = new ServerResponse();

        response.setMesssage(exception.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setTimeStamp(System.currentTimeMillis());
        response.setErrorType(exception.getClass().getSimpleName());
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ServerResponse> handleExceptionLogin(BadCredentialsException badCredentialsException){

        ServerResponse response = new ServerResponse();
        response.setMesssage("Unauthorized: Incorrect credentials");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setTimeStamp(System.currentTimeMillis());
        response.setErrorType("IncorrectCredentials");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler
    public  ResponseEntity<ServerResponse> handleGeneralException(Exception exception){

        ServerResponse response = new ServerResponse();

        response.setMesssage("Internal Server Error");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setTimeStamp(System.currentTimeMillis());
        response.setErrorType("GeneralException");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

    }
}
