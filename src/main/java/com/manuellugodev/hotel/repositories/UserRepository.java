package com.manuellugodev.hotel.repositories;

import com.manuellugodev.hotel.entity.Appointment;
import com.manuellugodev.hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String>{


    @Query(value = "select * from users where username=?1",nativeQuery = true)
    Optional<User> findByUsername(String username);
}
