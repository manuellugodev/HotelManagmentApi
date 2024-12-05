package com.manuellugodev.hotel.repositories;

import com.manuellugodev.hotel.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {


}
