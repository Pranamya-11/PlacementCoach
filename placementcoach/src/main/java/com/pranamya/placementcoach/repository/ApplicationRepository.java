package com.pranamya.placementcoach.repository;

import com.pranamya.placementcoach.model.Application;
import com.pranamya.placementcoach.model.Job;
import com.pranamya.placementcoach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    boolean existsByUserAndJob(User user, Job job);

    long countByUser(User user);

    long countByUserAndStatus(User user, String status);

    List<Application> findByUser(User user);
}
