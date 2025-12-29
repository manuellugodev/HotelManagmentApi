package com.manuellugodev.hotel.rest;

import com.manuellugodev.hotel.entity.DashboardStats;
import com.manuellugodev.hotel.entity.ServerResponse;
import com.manuellugodev.hotel.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<ServerResponse<DashboardStats>> getDashboardStats() {
        return ResponseEntity.ok(new ServerResponse<>(
                dashboardService.getDashboardStats(),
                HttpStatus.OK.value(),
                "Dashboard statistics retrieved successfully",
                null,
                System.currentTimeMillis()
        ));
    }
}
