package com.manuellugodev.hotel.services;

import com.manuellugodev.hotel.entity.Appointment;
import com.manuellugodev.hotel.entity.Guest;
import com.manuellugodev.hotel.entity.Room;
import com.manuellugodev.hotel.exception.AppointmentNotFoundException;
import com.manuellugodev.hotel.exception.GuestNotFoundException;
import com.manuellugodev.hotel.exception.RoomNotFoundException;
import com.manuellugodev.hotel.repositories.AppointmentRepository;
import com.manuellugodev.hotel.repositories.GuestRepository;
import com.manuellugodev.hotel.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {


    @Autowired
    GuestRepository guestRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    RoomRepository roomRepository;

    public void makeAppointment(int guestId, int roomId, Date startTime, Date endTime, String purpose,Double total) {

        Optional<Guest> optionalGuest = guestRepository.findById(guestId);

        if (!optionalGuest.isPresent()) {
            throw new GuestNotFoundException("Guest with ID" + guestId + "is not saved");
        }

        Guest guest = optionalGuest.get();
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room with ID " + roomId + " not found."));

        Appointment appointment = new Appointment();
        appointment.setGuest(guest);
        appointment.setRoom(room);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setStatus("pending");
        appointment.setPurpose(purpose);
        appointment.setTotal(total);


        appointmentRepository.save(appointment);
    }


    public List<Appointment> getAppointments() {

        return appointmentRepository.findAll();
    }

    public Page<Appointment> getAppointmentsPaginated(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    public List<Appointment> getAppointmentsByGuest(int guestId) {
        List<Appointment> appointments = appointmentRepository.findByGuest(guestId).orElseThrow(() -> new AppointmentNotFoundException("Appointments not found by guest " + guestId));

        if (appointments.isEmpty()) {
            throw new AppointmentNotFoundException("Appointments not found by guest " + guestId);
        }
        return appointments;
    }

    public List<Appointment> getUpcomingAppointmentsByGuestAndDate(int guestId, Date startDate) {
        List<Appointment> appointments = appointmentRepository.findUpcomingAppointments(guestId, startDate).orElseThrow(() -> new AppointmentNotFoundException("Appointments not found by guest " + guestId));

        if (appointments.isEmpty()) {
            throw new AppointmentNotFoundException("Appointments not found by guest and Date " + guestId);
        }
        return appointments;
    }

    public List<Appointment> getPastAppointmentsByGuestAndDate(int guestId, Date startDate) {
        List<Appointment> appointments = appointmentRepository.findPastAppointments(guestId, startDate).orElseThrow(() -> new AppointmentNotFoundException("Appointments not found by guest " + guestId));

        if (appointments.isEmpty()) {
            throw new AppointmentNotFoundException("Appointments not found by guest and Date " + guestId);
        }
        return appointments;
    }

    public String cancelAppointment(int id) {
        if (!appointmentRepository.existsById(id)) {
            throw new AppointmentNotFoundException("Appointments not found by id");
        }

        appointmentRepository.deleteById(id);
        return "Success";
    }

    public Appointment getAppointmentById(int id) {

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) {
            return appointment.get();
        }else {
            throw new AppointmentNotFoundException("Appointment Not found by Id");
        }

    }

    public Appointment updateAppointment(int id, Integer guestId, Integer roomId, Date startTime, Date endTime, String purpose, Double total, String status) {
        Appointment appointment = getAppointmentById(id);

        if (guestId != null) {
            Guest guest = guestRepository.findById(guestId)
                    .orElseThrow(() -> new GuestNotFoundException("Guest with ID " + guestId + " is not saved"));
            appointment.setGuest(guest);
        }

        if (roomId != null) {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RoomNotFoundException("Room with ID " + roomId + " not found."));
            appointment.setRoom(room);
        }

        if (startTime != null) {
            appointment.setStartTime(startTime);
        }

        if (endTime != null) {
            appointment.setEndTime(endTime);
        }

        if (purpose != null) {
            appointment.setPurpose(purpose);
        }

        if (total != null) {
            appointment.setTotal(total);
        }

        if (status != null) {
            appointment.setStatus(status);
        }

        return appointmentRepository.save(appointment);
    }
}
