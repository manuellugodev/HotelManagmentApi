package com.manuellugodev.hotel.services;

import com.manuellugodev.hotel.entity.Room;
import com.manuellugodev.hotel.exception.RoomNotAvailable;
import com.manuellugodev.hotel.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;


    public List<Room> getRooms(){
        return roomRepository.findAll();
    }

    public List<Room> getRoomsAvailable(Boolean available){
        Optional<List<Room>> result =roomRepository.findByAvailable();

        if(result.isPresent() && !result.get().isEmpty()){
            return result.get();
        }else {
            throw new RoomNotAvailable("We dont have rooms available this time");
        }

    }

}
