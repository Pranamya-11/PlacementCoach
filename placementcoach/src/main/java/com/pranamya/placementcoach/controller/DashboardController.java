package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.dto.DashboardResponse;
import com.pranamya.placementcoach.service.DashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/me")
    public DashboardResponse getDashboardStats(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return dashboardService.getDashboardStats(email);
    }
}