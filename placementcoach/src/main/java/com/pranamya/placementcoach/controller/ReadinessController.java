package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.dto.ReadinessResponse;
import com.pranamya.placementcoach.service.ReadinessService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/readiness")
public class ReadinessController {

    private final ReadinessService readinessService;

    public ReadinessController(ReadinessService readinessService) {
        this.readinessService = readinessService;
    }

    @GetMapping("/me")
    public ReadinessResponse getReadiness(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return readinessService.calculateReadiness(email);
    }
}