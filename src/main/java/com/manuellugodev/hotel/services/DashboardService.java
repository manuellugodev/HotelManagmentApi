package com.manuellugodev.hotel.services;

import com.manuellugodev.hotel.entity.DashboardStats;
import com.manuellugodev.hotel.repositories.AppointmentRepository;
import com.manuellugodev.hotel.repositories.GuestRepository;
import com.manuellugodev.hotel.repositories.RoomRepository;
import com.manuellugodev.hotel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public DashboardStats getDashboardStats() {
        Date today = new Date();

        long totalUsers = userRepository.count();
        long totalGuests = guestRepository.count();
        long totalRooms = roomRepository.count();
        long totalAppointments = appointmentRepository.count();

        long todayCheckIns = appointmentRepository.countTodayCheckIns(today);
        long todayCheckOuts = appointmentRepository.countTodayCheckOuts(today);
        long activeAppointments = appointmentRepository.countActiveAppointments(today);
        long pendingAppointments = appointmentRepository.countByStatus("pending");

        return new DashboardStats(
                totalUsers,
                totalGuests,
                totalRooms,
                totalAppointments,
                todayCheckIns,
                todayCheckOuts,
                activeAppointments,
                pendingAppointments
        );
    }
}
