package com.manuellugodev.hotel.services;

import com.manuellugodev.hotel.entity.User;
import com.manuellugodev.hotel.exception.UsernameAlreadyExist;
import com.manuellugodev.hotel.repositories.GuestRepository;
import com.manuellugodev.hotel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GuestRepository guestRepository;

    public User getDataProfileUser(String username){
        Optional<User> result = userRepository.findByUsername(username);

        if(result.isPresent()){

            User userResult = result.get();
            userResult.setPassword("");
            return userResult;
        }else {
            throw new UsernameNotFoundException("We cant find the user with username " + username);
        }
    }

    public User createUser(User user){
        Optional<User> userToRegister = userRepository.findByUsername(user.getUsername());

        if(userToRegister.isPresent()) {
            throw new UsernameAlreadyExist("Username Already Exist");
        }else {
            guestRepository.save(user.getGuestId());
            user.setEnabled(true);
            User result = userRepository.save(user);

            return result;
        }

    }
}
