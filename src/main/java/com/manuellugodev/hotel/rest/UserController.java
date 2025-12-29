package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.ServerResponse;
import com.manuellugodev.hotel.entity.User;
import com.manuellugodev.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ServerResponse<?>> getAllUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "username") String sortBy) {

        if (page != null && size != null) {
            // Return paginated results
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<User> users = userService.getAllUsersPaginated(pageable);
            return ResponseEntity.ok(new ServerResponse<>(
                    users,
                    HttpStatus.OK.value(),
                    "Users retrieved successfully (page " + page + " of " + users.getTotalPages() + ")",
                    null,
                    System.currentTimeMillis()
            ));
        } else {
            // Return all users
            return ResponseEntity.ok(new ServerResponse<>(
                    userService.getAllUsers(),
                    HttpStatus.OK.value(),
                    "Users retrieved successfully",
                    null,
                    System.currentTimeMillis()
            ));
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<ServerResponse<User>> updateUser(@PathVariable String username, @RequestBody User user) {
        User updatedUser = userService.updateUser(username, user);
        return ResponseEntity.ok(new ServerResponse<>(
                updatedUser,
                HttpStatus.OK.value(),
                "User updated successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ServerResponse<String>> deleteUser(@PathVariable String username) {
        return ResponseEntity.ok(new ServerResponse<>(
                userService.deleteUser(username),
                HttpStatus.OK.value(),
                "User deleted successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @PutMapping("/{username}/role")
    public ResponseEntity<ServerResponse<User>> updateUserRole(
            @PathVariable String username,
            @RequestParam String role) {
        User updatedUser = userService.updateUserRole(username, role);
        return ResponseEntity.ok(new ServerResponse<>(
                updatedUser,
                HttpStatus.OK.value(),
                "User role updated successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @PutMapping("/{username}/status")
    public ResponseEntity<ServerResponse<User>> toggleUserStatus(
            @PathVariable String username,
            @RequestParam boolean enabled) {
        User updatedUser = userService.toggleUserStatus(username, enabled);
        return ResponseEntity.ok(new ServerResponse<>(
                updatedUser,
                HttpStatus.OK.value(),
                "User status updated successfully",
                null,
                System.currentTimeMillis()
        ));
    }
}
