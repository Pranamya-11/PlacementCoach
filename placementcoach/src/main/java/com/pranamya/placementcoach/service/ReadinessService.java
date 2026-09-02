package com.pranamya.placementcoach.service;

import com.pranamya.placementcoach.dto.ReadinessResponse;
import com.pranamya.placementcoach.exception.ResourceNotFoundException;
import com.pranamya.placementcoach.model.Profile;
import com.pranamya.placementcoach.model.User;
import com.pranamya.placementcoach.repository.ApplicationRepository;
import com.pranamya.placementcoach.repository.ProfileRepository;
import com.pranamya.placementcoach.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReadinessService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ApplicationRepository applicationRepository;

    public ReadinessService(
            UserRepository userRepository,
            ProfileRepository profileRepository,
            ApplicationRepository applicationRepository
    ) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.applicationRepository = applicationRepository;
    }

    public ReadinessResponse calculateReadiness(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Profile not found"));

        long applications =
                applicationRepository.countByUser(user);

        int score = 0;

        // CGPA Score (25 Marks)
        Double cgpa = profile.getCgpa();

        if (cgpa != null) {
            if (cgpa >= 9.0) {
                score += 25;
            } else if (cgpa >= 8.0) {
                score += 20;
            } else if (cgpa >= 7.0) {
                score += 15;
            } else {
                score += 10;
            }
        }

        // Skills Score (20 Marks)
        if (profile.getSkills() != null &&
                !profile.getSkills().trim().isEmpty()) {
            score += 20;
        }

        // Resume Score (15 Marks)
        if (profile.getResumeLink() != null &&
                !profile.getResumeLink().trim().isEmpty()) {
            score += 15;
        }

        // LeetCode Score (20 Marks)
        Integer leetcode = profile.getLeetcodeCount();

        if (leetcode != null) {
            if (leetcode >= 200) {
                score += 20;
            } else if (leetcode >= 100) {
                score += 15;
            } else if (leetcode >= 50) {
                score += 10;
            } else if (leetcode > 0) {
                score += 5;
            }
        }

        // Applications Score (20 Marks)
        if (applications >= 10) {
            score += 20;
        } else if (applications >= 5) {
            score += 15;
        } else if (applications >= 1) {
            score += 10;
        }

        String level;
        String message;

        if (score >= 85) {
            level = "Excellent";
            message = "You are placement ready. Focus on interview preparation.";
        } else if (score >= 70) {
            level = "Good";
            message = "You have a good profile. Apply to more companies and improve your coding skills.";
        } else if (score >= 50) {
            level = "Average";
            message = "Improve your CGPA, coding skills, and resume to increase your readiness.";
        } else {
            level = "Needs Improvement";
            message = "Complete your profile and build your technical skills before placements.";
        }

        return new ReadinessResponse(score, level, message);
    }
}