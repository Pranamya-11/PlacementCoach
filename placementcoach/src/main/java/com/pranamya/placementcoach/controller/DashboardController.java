package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.dto.DashboardResponse;
import com.pranamya.placementcoach.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse getDashboardStats(
            @RequestParam Long userId
    ) {
        return dashboardService.getDashboardStats(userId);
    }
}