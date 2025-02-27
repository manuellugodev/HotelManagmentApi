package com.manuellugodev.hotel.repositories;

import com.manuellugodev.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = "SELECT rooms.* FROM rooms LEFT JOIN appointments ON rooms.RoomID = appointments.RoomID AND (CAST(?1 AS DATE) < CAST(appointments.EndTime AS DATE) AND CAST(?2 AS DATE) > CAST(appointments.StartTime AS DATE))  WHERE (rooms.Capacity>=?3  AND appointments.AppointmentID IS NULL)",nativeQuery = true)
    Optional<List<Room>> findByAvailable(String dStartTime, String dEndTime,int guests);
}
