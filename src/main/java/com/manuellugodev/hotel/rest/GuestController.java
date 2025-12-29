package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.Guest;
import com.manuellugodev.hotel.entity.ServerResponse;
import com.manuellugodev.hotel.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping
    public ResponseEntity<ServerResponse<?>> getAllGuests(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "guestId") String sortBy) {

        if (page != null && size != null) {
            // Return paginated results
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<Guest> guests = guestService.getAllGuestsPaginated(pageable);
            return ResponseEntity.ok(new ServerResponse<>(
                    guests,
                    HttpStatus.OK.value(),
                    "Guests retrieved successfully (page " + page + " of " + guests.getTotalPages() + ")",
                    null,
                    System.currentTimeMillis()
            ));
        } else {
            // Return all guests
            return ResponseEntity.ok(new ServerResponse<>(
                    guestService.getAllGuests(),
                    HttpStatus.OK.value(),
                    "Guests retrieved successfully",
                    null,
                    System.currentTimeMillis()
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServerResponse<Guest>> getGuestById(@PathVariable int id) {
        return ResponseEntity.ok(new ServerResponse<>(
                guestService.getGuestById(id),
                HttpStatus.OK.value(),
                "Guest retrieved successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @PostMapping
    public ResponseEntity<ServerResponse<Guest>> createGuest(@RequestBody Guest guest) {
        Guest createdGuest = guestService.createGuest(guest);
        return ResponseEntity.ok(new ServerResponse<>(
                createdGuest,
                HttpStatus.CREATED.value(),
                "Guest created successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServerResponse<Guest>> updateGuest(@PathVariable int id, @RequestBody Guest guest) {
        Guest updatedGuest = guestService.updateGuest(id, guest);
        return ResponseEntity.ok(new ServerResponse<>(
                updatedGuest,
                HttpStatus.OK.value(),
                "Guest updated successfully",
                null,
                System.currentTimeMillis()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServerResponse<String>> deleteGuest(@PathVariable int id) {
        return ResponseEntity.ok(new ServerResponse<>(
                guestService.deleteGuest(id),
                HttpStatus.OK.value(),
                "Guest deleted successfully",
                null,
                System.currentTimeMillis()
        ));
    }
}
