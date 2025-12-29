package com.manuellugodev.hotel.services;

import com.manuellugodev.hotel.entity.Guest;
import com.manuellugodev.hotel.entity.Role;
import com.manuellugodev.hotel.entity.User;
import com.manuellugodev.hotel.exception.UserNotFoundException;
import com.manuellugodev.hotel.exception.UsernameAlreadyExist;
import com.manuellugodev.hotel.repositories.GuestRepository;
import com.manuellugodev.hotel.repositories.RoleRepository;
import com.manuellugodev.hotel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    public User getDataProfileUser(String username){
        Optional<User> result = userRepository.findByUsername(username);

        if(result.isPresent()){

            User userResult = result.get();
            userResult.setPassword("");
            return userResult;
        }else {
            throw new UserNotFoundException("We cant find the user with username " + username);
        }
    }

    public User createUser(User user){
        Optional<User> userToRegister = userRepository.findByUsername(user.getUsername());

        if(userToRegister.isPresent()) {
            throw new UsernameAlreadyExist("Username Already Exist");
        }else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            guestRepository.save(user.getGuestId());

            Role role=new Role();
            role.setUsername(user.getUsername());
            role.setAuthority("ROLE_CLIENT"); // New users are clients by default

            user.setEnabled(true);
            User result = userRepository.save(user);

            roleRepository.save(role);

            return result;
        }

    }

    public java.util.List<User> getAllUsers(){
        java.util.List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(""));
        return users;
    }

    public Page<User> getAllUsersPaginated(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        users.forEach(user -> user.setPassword(""));
        return users;
    }

    public User updateUser(String username, User userDetails){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));

        // Update guest information if provided
        if(userDetails.getGuestId() != null){
            Guest guest = user.getGuestId();
            Guest guestDetails = userDetails.getGuestId();

            if(guestDetails.getFirstName() != null){
                guest.setFirstName(guestDetails.getFirstName());
            }
            if(guestDetails.getLastName() != null){
                guest.setLastName(guestDetails.getLastName());
            }
            if(guestDetails.getEmail() != null){
                guest.setEmail(guestDetails.getEmail());
            }
            if(guestDetails.getPhone() != null){
                guest.setPhone(guestDetails.getPhone());
            }
            guestRepository.save(guest);
        }

        // Update password if provided
        if(userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        // Update enabled status if provided
        if(userDetails.isEnabled() != user.isEnabled()){
            user.setEnabled(userDetails.isEnabled());
        }

        User updatedUser = userRepository.save(user);
        updatedUser.setPassword("");
        return updatedUser;
    }

    public String deleteUser(String username){
        if(!userRepository.existsById(username)){
            throw new UserNotFoundException("User with username " + username + " not found");
        }

        // Delete role first (foreign key constraint)
        roleRepository.deleteById(username);

        // Delete user
        userRepository.deleteById(username);

        return "Success";
    }

    public User updateUserRole(String username, String newRole){
        if(!userRepository.existsById(username)){
            throw new UserNotFoundException("User with username " + username + " not found");
        }

        // Validate role
        if(!newRole.equals("ROLE_CLIENT") && !newRole.equals("ROLE_EMPLOYEE") && !newRole.equals("ROLE_ADMIN")){
            throw new IllegalArgumentException("Invalid role. Must be ROLE_CLIENT, ROLE_EMPLOYEE, or ROLE_ADMIN");
        }

        Role role = roleRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("Role not found for user " + username));

        role.setAuthority(newRole);
        roleRepository.save(role);

        return getDataProfileUser(username);
    }

    public User toggleUserStatus(String username, boolean enabled){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));

        user.setEnabled(enabled);
        User updatedUser = userRepository.save(user);
        updatedUser.setPassword("");
        return updatedUser;
    }
}
