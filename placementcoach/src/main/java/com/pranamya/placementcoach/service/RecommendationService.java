package com.pranamya.placementcoach.service;

import com.pranamya.placementcoach.dto.RecommendationResponse;
import com.pranamya.placementcoach.exception.ResourceNotFoundException;
import com.pranamya.placementcoach.model.Job;
import com.pranamya.placementcoach.model.Profile;
import com.pranamya.placementcoach.model.User;
import com.pranamya.placementcoach.repository.ApplicationRepository;
import com.pranamya.placementcoach.repository.JobRepository;
import com.pranamya.placementcoach.repository.ProfileRepository;
import com.pranamya.placementcoach.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    public RecommendationService(
            UserRepository userRepository,
            ProfileRepository profileRepository,
            JobRepository jobRepository,
            ApplicationRepository applicationRepository
    ) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<RecommendationResponse> getRecommendations(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Profile not found"));

        List<Job> jobs = jobRepository.findAll();

        List<RecommendationResponse> recommendations = new ArrayList<>();

        for (Job job : jobs) {

            // 1. Ignore inactive jobs
            if (!job.isActive()) {
                continue;
            }

            // 2. Ignore expired jobs
            if (job.getDeadline() != null &&
                    job.getDeadline().isBefore(LocalDate.now())) {
                continue;
            }

            // 3. Ignore jobs where student's CGPA is too low
            if (profile.getCgpa() == null ||
                    profile.getCgpa() < job.getMinimumCgpa()) {
                continue;
            }

            // 4. Ignore jobs already applied for
            if (applicationRepository.existsByUserAndJob(user, job)) {
                continue;
            }

            // 5. Calculate skill match
            int matchScore = calculateSkillMatch(
                    profile.getSkills(),
                    job.getRequiredSkills()
            );

            if (matchScore > 0) {
                recommendations.add(
                        new RecommendationResponse(
                                job.getCompany(),
                                job.getRole(),
                                job.getLocation(),
                                matchScore
                        )
                );
            }
        }

        return recommendations;
    }

    private int calculateSkillMatch(
            String studentSkills,
            String requiredSkills
    ) {

        if (studentSkills == null ||
                studentSkills.trim().isEmpty() ||
                requiredSkills == null ||
                requiredSkills.trim().isEmpty()) {
            return 0;
        }

        String[] studentSkillArray =
                studentSkills.toLowerCase().split(",");

        String[] requiredSkillArray =
                requiredSkills.toLowerCase().split(",");

        int matchedSkills = 0;

        for (String requiredSkill : requiredSkillArray) {

            String required = requiredSkill.trim();

            for (String studentSkill : studentSkillArray) {

                String student = studentSkill.trim();

                if (student.equals(required)) {
                    matchedSkills++;
                    break;
                }
            }
        }

        return (matchedSkills * 100) / requiredSkillArray.length;
    }
}