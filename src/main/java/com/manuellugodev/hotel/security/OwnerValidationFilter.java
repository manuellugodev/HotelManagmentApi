package com.manuellugodev.hotel.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manuellugodev.hotel.entity.Appointment;
import com.manuellugodev.hotel.entity.ServerResponse;
import com.manuellugodev.hotel.entity.User;
import com.manuellugodev.hotel.exception.AccessDeniedResourceException;
import com.manuellugodev.hotel.exception.AppointmentNotFoundException;
import com.manuellugodev.hotel.exception.UserNotFoundException;
import com.manuellugodev.hotel.services.AppointmentService;
import com.manuellugodev.hotel.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class OwnerValidationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//Guess that is not authentication request

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            boolean hasAdminRole = false;

            if (authentication != null && authentication.isAuthenticated()) {

                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                    hasAdminRole = authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));


                    boolean isOwner = isOwnerResource(authentication.getName(), request);

                    if (!hasAdminRole) {

                        if (!isOwner) {
                            logger.info("User does not have permission to access this resource.");
                            throw new AccessDeniedResourceException("User does not have permission to access this resource--Should be the owner or have admin role");
                        }

                    }

            }


            filterChain.doFilter(request, response);

        }catch (AccessDeniedResourceException accessDeniedResourceException){
            ServerResponse<String> serverResponse =new ServerResponse();

            serverResponse.setErrorType(accessDeniedResourceException.getClass().getSimpleName());
            serverResponse.setTimeStamp(System.currentTimeMillis());
            serverResponse.setMesssage(accessDeniedResourceException.getMessage());
            serverResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getWriter(), serverResponse);
        }

    }

    private boolean isOwnerResource(String username, HttpServletRequest request) {

        User user = userService.getDataProfileUser(username);

        if (user != null) {
            try {

                if(request.getMethod().equalsIgnoreCase("DELETE")){
                    Appointment appointment = appointmentService.getAppointmentById(Integer.parseInt(request.getParameter("id")));
                    return appointment.getGuest().getGuestId() == user.getGuestId().getGuestId();
                }else if (request.getMethod().equalsIgnoreCase("GET")){
                    if(request.getRequestURI().contains("guest")){
                        String[] requestParts = request.getRequestURI().split("/");
                        int appointmentId=getAppointmentIdRequest(requestParts);
                        return appointmentId == user.getGuestId().getGuestId();

                    }else if(request.getRequestURI().contains("user")){
                        String usernameByParam=  request.getParameter("username");
                        return username.equalsIgnoreCase(usernameByParam);

                    }
                }else if(request.getMethod().equalsIgnoreCase("POST")){
                    if (request.getRequestURI().contains("appointment")){
                        String userIdByParam=request.getParameter("guestId");
                        try {
                            User userDetail = userService.getDataProfileUser(username);

                            return String.valueOf(userDetail.getGuestId().getGuestId()).equalsIgnoreCase(userIdByParam);

                        }catch (UserNotFoundException ignored){

                        }
                    }

                }


            } catch (AppointmentNotFoundException notFoundException) {
                return false;
            }
        } else {
            logger.error("User with username " + username + "don't found");
            throw new UserNotFoundException("The user was not found by username");
        }

        return true;
    }

    private int getAppointmentIdRequest(String[] requestParts) {
        for(String r:requestParts){
            try{
               return Integer.parseInt(r);
            }catch (NumberFormatException numberFormatException){

            }
        }
        return 0;
    }

    private boolean haveAccess() {
        return true;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
