package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.*;
import com.manuellugodev.hotel.exception.*;
import com.manuellugodev.hotel.security.JwtUtil;
import com.manuellugodev.hotel.services.AppointmentService;
import com.manuellugodev.hotel.services.RoomService;
import com.manuellugodev.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class HotelController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private  UserService userService;

    @Autowired
    private RoomService roomService;
    @PostMapping("/appointment")
    public ResponseEntity<String> makeAppointment(
            @RequestParam int guestId,
            @RequestParam int roomId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
            @RequestParam String purpose) {

            appointmentService.makeAppointment(guestId, roomId, startTime, endTime, purpose);
            return ResponseEntity.ok("\"Appointment made successfully\"");

    }


    @GetMapping("/appointment")
    public List<Appointment> getAppointments() {
        return appointmentService.getAppointments();
    }

    @GetMapping(value = "/appointment/guest/{guestId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByGuest(@PathVariable int guestId) {

        return ResponseEntity.ok(appointmentService.getAppointmentsByGuest(guestId));

    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms(
            @RequestParam int guests,
            @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") Date dStartTime,
            @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd")Date dEndTime) {

        return ResponseEntity.ok(roomService.getRoomsAvailable(dStartTime,dEndTime,guests));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> doLogin(@RequestBody AuthenticationRequest authRequest){


        UsernamePasswordAuthenticationToken toke  = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(toke);


        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<User> getUserProfileById(@PathVariable String username ){
        try {
            return ResponseEntity.ok(userService.getDataProfileUser(username));
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/user/register")
    public ResponseEntity<Map<String, String>> doRegister(@RequestBody SignUpRequest request){

            User userToSave = new User();
            userToSave.setUsername(request.getUsername());
            userToSave.setPassword(request.getPassword());

            Guest infUser= new Guest();
            infUser.setFirstName(request.getFirstName());
            infUser.setEmail(request.getEmail());
            infUser.setLastName(request.getLastName());
            infUser.setPhone(request.getPhone());
            userToSave.setGuestId(infUser);
            User result =userService.createUser(userToSave);


        return ResponseEntity.status(HttpStatus.OK).build();

    }


    @ExceptionHandler
    public ResponseEntity<ServerErrorResponse> handleException(NotFoundException exception){

        ServerErrorResponse response = new ServerErrorResponse();

        response.setMesssage(exception.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimeStamp(System.currentTimeMillis());
        response.setExceptionType(exception.getClass().getSimpleName());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler
    public ResponseEntity<ServerErrorResponse> handleException(ConflictException exception){

        ServerErrorResponse response = new ServerErrorResponse();

        response.setMesssage(exception.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setTimeStamp(System.currentTimeMillis());
        response.setExceptionType(exception.getClass().getSimpleName());
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<AuthenticationResponse> handleExceptionLogin(BadCredentialsException badCredentialsException){

       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse("","Unauthorized: Incorrect credentials"));
    }

    @ExceptionHandler
    public  ResponseEntity<ServerErrorResponse> handleGeneralException(Exception exception){

        ServerErrorResponse response = new ServerErrorResponse();

        response.setMesssage("Internal Server Error");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setTimeStamp(System.currentTimeMillis());
        response.setExceptionType("GeneralException");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

    }

}
