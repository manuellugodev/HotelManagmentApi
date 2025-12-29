package com.manuellugodev.hotel.services;

import com.manuellugodev.hotel.entity.Guest;
import com.manuellugodev.hotel.exception.GuestDeletionException;
import com.manuellugodev.hotel.exception.GuestNotFoundException;
import com.manuellugodev.hotel.repositories.AppointmentRepository;
import com.manuellugodev.hotel.repositories.GuestRepository;
import com.manuellugodev.hotel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Page<Guest> getAllGuestsPaginated(Pageable pageable) {
        return guestRepository.findAll(pageable);
    }

    public Guest getGuestById(int id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException("Guest with ID " + id + " not found"));
    }

    public Guest createGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public Guest updateGuest(int id, Guest guestDetails) {
        Guest guest = getGuestById(id);

        if (guestDetails.getFirstName() != null) {
            guest.setFirstName(guestDetails.getFirstName());
        }
        if (guestDetails.getLastName() != null) {
            guest.setLastName(guestDetails.getLastName());
        }
        if (guestDetails.getEmail() != null) {
            guest.setEmail(guestDetails.getEmail());
        }
        if (guestDetails.getPhone() != null) {
            guest.setPhone(guestDetails.getPhone());
        }

        return guestRepository.save(guest);
    }

    public String deleteGuest(int id) {
        if (!guestRepository.existsById(id)) {
            throw new GuestNotFoundException("Guest with ID " + id + " not found");
        }

        // Check if guest has a user account
        if (userRepository.existsByGuestId(id)) {
            throw new GuestDeletionException("Cannot delete guest - guest has an associated user account. Delete the user account first.");
        }

        // Check if guest has appointments
        if (appointmentRepository.existsByGuestId(id)) {
            throw new GuestDeletionException("Cannot delete guest - guest has appointment history. Cancel or delete appointments first.");
        }

        guestRepository.deleteById(id);
        return "Success";
    }
}
