package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.dto.RecentApplicationResponse;
import com.pranamya.placementcoach.model.Application;
import com.pranamya.placementcoach.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public Application createApplication(@RequestBody Application application) {
        return applicationService.saveApplication(application);
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    // IMPORTANT: put this before /{id}
    @GetMapping("/recent")
    public List<RecentApplicationResponse> getRecentApplications(
            @RequestParam Long userId
    ) {
        return applicationService.getRecentApplications(userId);
    }

    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id);
    }

    @PutMapping("/{id}/status")
    public Application updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return applicationService.updateApplicationStatus(id, status);
    }

    @PostMapping("/jobs/{jobId}/apply")
    public Application applyForJob(
            @PathVariable Long jobId,
            @RequestParam Long userId
    ) {
        return applicationService.applyForJob(jobId, userId);
    }
}