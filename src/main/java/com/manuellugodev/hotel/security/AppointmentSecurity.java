package com.manuellugodev.hotel.security;

import com.manuellugodev.hotel.repositories.AppointmentRepository;

public class AppointmentSecurity {


    private final AppointmentRepository repository;

    public AppointmentSecurity(AppointmentRepository repository) {
        this.repository = repository;
    }


}
