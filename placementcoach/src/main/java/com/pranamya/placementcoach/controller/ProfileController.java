package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.model.Profile;
import com.pranamya.placementcoach.service.ProfileService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(
            ProfileService profileService
    ) {
        this.profileService = profileService;
    }

    @PostMapping
    public Profile createProfile(
            @RequestBody Profile profile
    ) {
        return profileService.saveProfile(profile);
    }

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }
    @PutMapping("/{id}")
    public Profile updateProfile(
            @PathVariable Long id,
            @RequestBody Profile profile
    ) {
        return profileService.updateProfile(
                id,
                profile
        );
    }
}