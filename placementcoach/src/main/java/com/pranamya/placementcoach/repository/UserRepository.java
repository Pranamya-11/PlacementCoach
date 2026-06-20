package com.pranamya.placementcoach.repository;

import com.pranamya.placementcoach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}