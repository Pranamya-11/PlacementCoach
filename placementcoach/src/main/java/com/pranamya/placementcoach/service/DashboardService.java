package com.pranamya.placementcoach.service;

import com.pranamya.placementcoach.dto.DashboardResponse;

import com.pranamya.placementcoach.exception.ResourceNotFoundException;
import com.pranamya.placementcoach.model.User;
import com.pranamya.placementcoach.repository.ApplicationRepository;
import com.pranamya.placementcoach.repository.JobRepository;
import com.pranamya.placementcoach.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
   private final ApplicationRepository applicationRepository;
   private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public DashboardService(ApplicationRepository applicationRepository,
                            UserRepository userRepository,JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository=jobRepository;
    }

    public DashboardResponse getDashboardStats(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        long appliedJobs = applicationRepository.countByUser(user);
        long pending = applicationRepository.countByUserAndStatus(user, "Applied");
        long shortlisted = applicationRepository.countByUserAndStatus(user, "Shortlisted");
        long rejected = applicationRepository.countByUserAndStatus(user, "Rejected");
        long totalJobs=jobRepository.count();
        long activeJobs = jobRepository.countByActive(true);
        return new DashboardResponse(
                appliedJobs,
                pending,
                shortlisted,
                rejected,
                totalJobs,
                activeJobs
        );
    }
}
