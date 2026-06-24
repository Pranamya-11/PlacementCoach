package com.pranamya.placementcoach.service;

import com.pranamya.placementcoach.model.Profile;
import com.pranamya.placementcoach.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(
            ProfileRepository profileRepository
    ) {
        this.profileRepository = profileRepository;
    }

    public Profile saveProfile(
            Profile profile
    ) {
        return profileRepository.save(profile);
    }
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
    public Profile updateProfile(
            Long id,
            Profile profile
    ) {

        Profile existingProfile =
                profileRepository.findById(id)
                        .orElseThrow();

        existingProfile.setUsn(profile.getUsn());
        existingProfile.setBranch(profile.getBranch());
        existingProfile.setCgpa(profile.getCgpa());
        existingProfile.setSkills(profile.getSkills());
        existingProfile.setLeetcodeCount(
                profile.getLeetcodeCount()
        );
        existingProfile.setResumeLink(
                profile.getResumeLink()
        );

        return profileRepository.save(
                existingProfile
        );
    }
}