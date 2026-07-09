package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.dto.ReadinessResponse;
import com.pranamya.placementcoach.service.ReadinessService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/readiness")
public class ReadinessController {

    private final ReadinessService readinessService;

    public ReadinessController(ReadinessService readinessService) {
        this.readinessService = readinessService;
    }

    @GetMapping
    public ReadinessResponse getReadiness(
            @RequestParam Long userId
    ) {
        return readinessService.calculateReadiness(userId);
    }
}