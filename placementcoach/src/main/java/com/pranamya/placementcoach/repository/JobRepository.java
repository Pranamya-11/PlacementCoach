package com.pranamya.placementcoach.repository;

import com.pranamya.placementcoach.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository
extends JpaRepository<Job,Long> {

}
