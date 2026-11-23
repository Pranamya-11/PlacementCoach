
package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.model.Profile;
import com.pranamya.placementcoach.service.ProfileService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public Profile getMyProfile(Authentication authentication) {

        String email = authentication.getName();

        return profileService.getProfileByEmail(email);
    }

    @PostMapping("/me")
    public Profile createOrUpdateProfile(
            @RequestBody Profile profile,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return profileService.saveProfile(profile, email);
    }

    @PutMapping("/me")
    public Profile updateMyProfile(
            @RequestBody Profile profile,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return profileService.saveProfile(profile, email);
    }
}
