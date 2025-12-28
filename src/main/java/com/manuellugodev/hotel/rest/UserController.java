package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.ServerResponse;
import com.manuellugodev.hotel.entity.User;
import com.manuellugodev.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ServerResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(new ServerResponse<>(
                userService.getAllUsers(),
                HttpStatus.OK.value(),
                "Users retrieved successfully",
                null,
                System.currentTimeMillis()
        ));
    }
}
