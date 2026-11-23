
package com.pranamya.placementcoach.service;

import com.pranamya.placementcoach.exception.ResourceNotFoundException;
import com.pranamya.placementcoach.model.Profile;
import com.pranamya.placementcoach.model.User;
import com.pranamya.placementcoach.repository.ProfileRepository;
import com.pranamya.placementcoach.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(
            ProfileRepository profileRepository,
            UserRepository userRepository
    ) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public Profile getProfileByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return profileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Profile not found"));
    }

    public Profile saveProfile(Profile profile, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Profile existingProfile = profileRepository.findByUser(user)
                .orElse(new Profile());

        existingProfile.setUser(user);
        existingProfile.setUsn(profile.getUsn());
        existingProfile.setBranch(profile.getBranch());
        existingProfile.setCgpa(profile.getCgpa());
        existingProfile.setSkills(profile.getSkills());
        existingProfile.setLeetcodeCount(profile.getLeetcodeCount());
        existingProfile.setResumeLink(profile.getResumeLink());

        return profileRepository.save(existingProfile);
    }
}
