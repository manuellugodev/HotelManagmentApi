package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.Appointment;
import com.manuellugodev.hotel.entity.Room;
import com.manuellugodev.hotel.exception.GuestNotFoundException;
import com.manuellugodev.hotel.exception.RoomNotAvailable;
import com.manuellugodev.hotel.exception.RoomNotFoundException;
import com.manuellugodev.hotel.services.AppointmentService;
import com.manuellugodev.hotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class HotelController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private RoomService roomService;

    @PostMapping("/appointment")
    public ResponseEntity<String> makeAppointment(
            @RequestParam int guestId,
            @RequestParam int roomId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endTime,
            @RequestParam String purpose
    ) {
        try {
            appointmentService.makeAppointment(guestId, roomId, startTime, endTime, purpose);
            return ResponseEntity.ok("Appointment made successfully.");
        } catch (GuestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RoomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RoomNotAvailable e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while making the appointment.");
        }
    }

    @GetMapping("/appointment")
    public List<Appointment> getAppointments() {
        return appointmentService.getAppointments();
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms(@RequestParam Boolean available) {

        try {
            return ResponseEntity.ok(roomService.getRoomsAvailable(available));
        } catch (RoomNotAvailable roomNotAvailable) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
