package com.pranamya.placementcoach.service;

import com.pranamya.placementcoach.exception.ResourceNotFoundException;
import com.pranamya.placementcoach.model.Job;
import com.pranamya.placementcoach.repository.JobRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository){
        this.jobRepository=jobRepository;

    }

    public Job  saveJob(Job job){
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
    }
    public List<Job> getUpcomingJobs() {

        LocalDate today = LocalDate.now();

        return jobRepository.findAll()
                .stream()
                .filter(job -> job.isActive())
                .filter(job -> job.getDeadline() != null)
                .filter(job -> !job.getDeadline().isBefore(today))
                .sorted((job1, job2) ->
                        job1.getDeadline().compareTo(job2.getDeadline()))
                .toList();
    }
}
