package com.manuellugodev.hotel.repositories;

import com.manuellugodev.hotel.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    Optional<Appointment> findByRoomIdAndStatus(Integer roomId, String status);

}
