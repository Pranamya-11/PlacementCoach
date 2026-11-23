
package com.pranamya.placementcoach.service;

import com.pranamya.placementcoach.dto.RecentApplicationResponse;
import com.pranamya.placementcoach.exception.DuplicateApplicationException;
import com.pranamya.placementcoach.exception.ResourceNotFoundException;
import com.pranamya.placementcoach.model.Application;
import com.pranamya.placementcoach.model.Job;
import com.pranamya.placementcoach.model.User;
import com.pranamya.placementcoach.repository.ApplicationRepository;
import com.pranamya.placementcoach.repository.JobRepository;
import com.pranamya.placementcoach.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            UserRepository userRepository,
            JobRepository jobRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    // Save a new application
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get applications belonging to a particular user
    public List<Application> getApplicationsByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return applicationRepository.findByUser(user);
    }

    // Get application by ID and verify its owner
    public Application getApplicationByIdAndUserId(
            Long id, Long userId) {

        return applicationRepository.findByIdAndUser_Id(id, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));
    }

    // Update application status only for the owner
    public Application updateApplicationStatus(
            Long id, String status, Long userId) {

        Application application =
                getApplicationByIdAndUserId(id, userId);

        application.setStatus(status);

        return applicationRepository.save(application);
    }

    // Apply for a job
    public Application applyForJob(Long jobId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        if (applicationRepository.existsByUserAndJob(user, job)) {
            throw new DuplicateApplicationException(
                    "You have already applied for this job"
            );
        }

        Application application = new Application();

        application.setUser(user);
        application.setJob(job);
        application.setStatus("Applied");
        application.setAppliedDate(LocalDate.now());

        return applicationRepository.save(application);
    }

    // Get recent applications belonging to a user
    public List<RecentApplicationResponse> getRecentApplications(
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        List<Application> applications =
                applicationRepository.findByUser(user);

        return applications.stream()
                .map(application -> new RecentApplicationResponse(
                        application.getJob().getCompany(),
                        application.getJob().getRole(),
                        application.getStatus(),
                        application.getAppliedDate()
                ))
                .toList();
    }
}
