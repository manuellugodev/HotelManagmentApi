package com.manuellugodev.hotel.services;

import com.manuellugodev.hotel.entity.Room;
import com.manuellugodev.hotel.exception.RoomNotAvailable;
import com.manuellugodev.hotel.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;


    public List<Room> getRooms(){
        return roomRepository.findAll();
    }

    public List<Room> getRoomsAvailable(Date dStartTime,Date dEndTime,int guests){
        SimpleDateFormat outputFormat=new SimpleDateFormat("yyyy-MM-dd");
        Optional<List<Room>> result =roomRepository.findByAvailable(outputFormat.format(dStartTime),outputFormat.format(dEndTime),guests);

        if(result.isPresent() && !result.get().isEmpty()){
            return result.get();
        }else {
            throw new RoomNotAvailable("We dont have rooms available this time");
        }

    }

    public Room createRoom(Room room){
        return roomRepository.save(room);
    }

    public Room getRoomById(int id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new com.manuellugodev.hotel.exception.RoomNotFoundException("Room with ID " + id + " not found"));
    }

    public Room updateRoom(int id, Room roomDetails){
        Room room = getRoomById(id);

        if(roomDetails.getRoomNumber() != null){
            room.setRoomNumber(roomDetails.getRoomNumber());
        }
        if(roomDetails.getRoomType() != null){
            room.setRoomType(roomDetails.getRoomType());
        }
        if(roomDetails.getCapacity() > 0){
            room.setCapacity(roomDetails.getCapacity());
        }
        if(roomDetails.getDescription() != null){
            room.setDescription(roomDetails.getDescription());
        }
        if(roomDetails.getPrice() != null){
            room.setPrice(roomDetails.getPrice());
        }
        if(roomDetails.getImage() != null){
            room.setImage(roomDetails.getImage());
        }

        return roomRepository.save(room);
    }

    public String deleteRoom(int id){
        if (!roomRepository.existsById(id)) {
            throw new com.manuellugodev.hotel.exception.RoomNotFoundException("Room with ID " + id + " not found");
        }
        roomRepository.deleteById(id);
        return "Success";
    }

}
