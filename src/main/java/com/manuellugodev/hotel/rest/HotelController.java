package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.*;
import com.manuellugodev.hotel.security.JwtUtil;
import com.manuellugodev.hotel.services.AppointmentService;
import com.manuellugodev.hotel.services.RoomService;
import com.manuellugodev.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public ResponseEntity<ServerResponse<List<Appointment>>> getAppointmentsByGuest(@PathVariable int guestId) {

        return ResponseEntity.ok(new ServerResponse<>(appointmentService.getAppointmentsByGuest(guestId), HttpStatus.OK.value(),"Appointments retrieved successfully",null,System.currentTimeMillis()));

    }

    @GetMapping("/rooms")
    public ResponseEntity<ServerResponse<List<Room>>> getRooms(
            @RequestParam int guests,
            @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") Date dStartTime,
            @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd")Date dEndTime) {



        return ResponseEntity.ok(new ServerResponse<>(roomService.getRoomsAvailable(dStartTime,dEndTime,guests),HttpStatus.OK.value(),"Rooms retrieved successfully",null,System.currentTimeMillis()));

    }

    @PostMapping("/login")
    public ResponseEntity<ServerResponse<AuthenticationResponse>> doLogin(@RequestBody AuthenticationRequest authRequest){


        UsernamePasswordAuthenticationToken toke  = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(toke);


        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new ServerResponse<>(new AuthenticationResponse(jwt),HttpStatus.OK.value(),"Login Success",null,System.currentTimeMillis()));
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<ServerResponse<User>> getUserProfileById(@PathVariable String username ){

        User userResult  =userService.getDataProfileUser(username);
        ServerResponse<User> response = new ServerResponse<>(userResult,HttpStatus.OK.value(),
                "User retrieved successfully",null,System.currentTimeMillis()
        );

        return ResponseEntity.ok(response);

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



}
