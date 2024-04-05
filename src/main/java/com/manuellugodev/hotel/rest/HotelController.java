package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.Appointment;
import com.manuellugodev.hotel.entity.Room;
import com.manuellugodev.hotel.exception.AppointmentNotFoundException;
import com.manuellugodev.hotel.exception.GuestNotFoundException;
import com.manuellugodev.hotel.exception.RoomNotAvailable;
import com.manuellugodev.hotel.exception.RoomNotFoundException;
import com.manuellugodev.hotel.services.AppointmentService;
import com.manuellugodev.hotel.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
            @RequestParam Date startTime,
            @RequestParam Date endTime,
            @RequestParam String purpose) {
        try {
            appointmentService.makeAppointment(guestId, roomId, startTime, endTime, purpose);
            return ResponseEntity.ok("\"Appointment made successfully\"");
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

    @GetMapping(value = "/appointment/guest/{guestId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByGuest(@PathVariable int guestId) {

        try {
            return ResponseEntity.ok(appointmentService.getAppointmentsByGuest(guestId));
        }catch (AppointmentNotFoundException appointmentNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms(
            @RequestParam int guests,
            @RequestParam Date dStartTime,
            @RequestParam Date dEndTime) {

        try {
            return ResponseEntity.ok(roomService.getRoomsAvailable(dStartTime,dEndTime,guests));
        } catch (RoomNotAvailable roomNotAvailable) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
