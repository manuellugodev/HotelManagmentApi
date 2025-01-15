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

    public List<Appointment> getAppointmentsByGuest(int guestId) {
        List<Appointment> appointments = appointmentRepository.findByGuest(guestId).orElseThrow(() -> new AppointmentNotFoundException("Appointmens not found by guest " + guestId));

        if(appointments.isEmpty()){
            throw new AppointmentNotFoundException("Appointmens not found by guest " + guestId);
        }
        return appointments;
    }

    public List<Appointment> getUpcomingAppointmentsByGuestAndDate(int guestId, Date startDate) {
        List<Appointment> appointments = appointmentRepository.findUpcomingAppointments(guestId,startDate).orElseThrow(() -> new AppointmentNotFoundException("Appointmens not found by guest " + guestId));

        if(appointments.isEmpty()){
            throw new AppointmentNotFoundException("Appointmens not found by guest and Date " + guestId);
        }
        return appointments;
    }

    public List<Appointment> getPastAppointmentsByGuestAndDate(int guestId, Date startDate) {
        List<Appointment> appointments = appointmentRepository.findPastAppointments(guestId,startDate).orElseThrow(() -> new AppointmentNotFoundException("Appointmens not found by guest " + guestId));

        if(appointments.isEmpty()){
            throw new AppointmentNotFoundException("Appointmens not found by guest and Date " + guestId);
        }
        return appointments;
    }

    public String cancelAppointment(int id) {
        try {
            appointmentRepository.deleteById(id);
            return "Success";
        }catch (NullPointerException exception){
            throw new AppointmentNotFoundException("Appointments not found by id");
        }

    }
}
