package com.manuellugodev.hotel.repositories;

import com.manuellugodev.hotel.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    Optional<Appointment> findByRoomIdAndStatus(Integer roomId, String status);

    @Query(value = "select * from appointments where GuestID=?1",nativeQuery = true)
    Optional<List<Appointment>> findByGuest(int guestId);

    @Query(value = "select * FROM appointments where GuestID=?1 AND starttime>=?2",nativeQuery = true)
    Optional<List<Appointment>> findUpcomingAppointments(int guestId,Date startDate);

    @Query(value = "select * FROM appointments where GuestID=?1 AND starttime<?2",nativeQuery = true)
    Optional<List<Appointment>> findPastAppointments(int guestId,Date startDate);

    @Query(value = "select count(*) > 0 from appointments where GuestID=?1",nativeQuery = true)
    boolean existsByGuestId(int guestId);

    @Query(value = "select count(*) from appointments where CAST(starttime AS DATE) = CAST(?1 AS DATE)",nativeQuery = true)
    long countTodayCheckIns(Date today);

    @Query(value = "select count(*) from appointments where CAST(endtime AS DATE) = CAST(?1 AS DATE)",nativeQuery = true)
    long countTodayCheckOuts(Date today);

    @Query(value = "select count(*) from appointments where CAST(?1 AS DATE) BETWEEN CAST(starttime AS DATE) AND CAST(endtime AS DATE)",nativeQuery = true)
    long countActiveAppointments(Date today);

    @Query(value = "select count(*) from appointments where status = ?1",nativeQuery = true)
    long countByStatus(String status);

}
