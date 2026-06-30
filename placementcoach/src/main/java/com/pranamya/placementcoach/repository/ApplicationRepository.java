package com.pranamya.placementcoach.repository;

import com.pranamya.placementcoach.model.Application;
import com.pranamya.placementcoach.model.Job;
import com.pranamya.placementcoach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository <Application,Long>{
    boolean existsByUserAndJob(User user, Job job);
}
