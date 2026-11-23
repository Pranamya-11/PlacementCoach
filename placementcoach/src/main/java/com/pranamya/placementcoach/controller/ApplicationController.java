
package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.dto.RecentApplicationResponse;
import com.pranamya.placementcoach.model.Application;
import com.pranamya.placementcoach.model.User;
import com.pranamya.placementcoach.repository.UserRepository;
import com.pranamya.placementcoach.service.ApplicationService;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserRepository userRepository;

    public ApplicationController(
            ApplicationService applicationService,
            UserRepository userRepository
    ) {
        this.applicationService = applicationService;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));
    }

    @PostMapping
    public Application createApplication(
            @RequestBody Application application,
            Authentication authentication
    ) {
        User user = getLoggedInUser(authentication);

        application.setUser(user);

        return applicationService.saveApplication(application);
    }

    @GetMapping
    public List<Application> getAllApplications(
            Authentication authentication
    ) {
        User user = getLoggedInUser(authentication);

        return applicationService.getApplicationsByUserId(user.getId());
    }

    @GetMapping("/recent")
    public List<RecentApplicationResponse> getRecentApplications(
            Authentication authentication
    ) {
        User user = getLoggedInUser(authentication);

        return applicationService.getRecentApplications(user.getId());
    }

    @GetMapping("/{id}")
    public Application getApplicationById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = getLoggedInUser(authentication);

        return applicationService.getApplicationByIdAndUserId(
                id, user.getId()
        );
    }

    @PutMapping("/{id}/status")
    public Application updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam String status,
            Authentication authentication
    ) {
        User user = getLoggedInUser(authentication);

        return applicationService.updateApplicationStatus(
                id, status, user.getId()
        );
    }

    @PostMapping("/jobs/{jobId}/apply")
    public Application applyForJob(
            @PathVariable Long jobId,
            Authentication authentication
    ) {
        User user = getLoggedInUser(authentication);

        return applicationService.applyForJob(jobId, user.getId());
    }
}
