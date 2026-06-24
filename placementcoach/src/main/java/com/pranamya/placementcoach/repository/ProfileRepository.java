package com.pranamya.placementcoach.repository;

import com.pranamya.placementcoach.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository
        extends JpaRepository<Profile, Long> {
}