package com.manuellugodev.hotel.services;

import com.manuellugodev.hotel.entity.User;
import com.manuellugodev.hotel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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
}
