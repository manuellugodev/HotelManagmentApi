package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.Room;
import com.manuellugodev.hotel.entity.ServerResponse;
import com.manuellugodev.hotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/all")
    public ResponseEntity<ServerResponse<List<Room>>> getAllRooms() {
        return ResponseEntity.ok(new ServerResponse<>(
                roomService.getRooms(),
                HttpStatus.OK.value(),
                "All rooms retrieved successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServerResponse<Room>> getRoomById(@PathVariable int id) {
        return ResponseEntity.ok(new ServerResponse<>(
                roomService.getRoomById(id),
                HttpStatus.OK.value(),
                "Room retrieved successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @PostMapping
    public ResponseEntity<ServerResponse<Room>> createRoom(@RequestBody Room room) {
        Room createdRoom = roomService.createRoom(room);
        return ResponseEntity.ok(new ServerResponse<>(
                createdRoom,
                HttpStatus.CREATED.value(),
                "Room created successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServerResponse<Room>> updateRoom(@PathVariable int id, @RequestBody Room room) {
        Room updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok(new ServerResponse<>(
                updatedRoom,
                HttpStatus.OK.value(),
                "Room updated successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServerResponse<String>> deleteRoom(@PathVariable int id) {
        return ResponseEntity.ok(new ServerResponse<>(
                roomService.deleteRoom(id),
                HttpStatus.OK.value(),
                "Room deleted successfully",
                null,
                System.currentTimeMillis()
        ));
    }
}
