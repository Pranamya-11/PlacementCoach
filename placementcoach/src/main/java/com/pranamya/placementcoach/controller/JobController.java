package com.pranamya.placementcoach.controller;


import com.pranamya.placementcoach.model.Job;
import com.pranamya.placementcoach.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
     @PostMapping
    public Job createJob(
            @RequestBody Job job
     ){
        return jobService.saveJob(job);
     }

    @GetMapping
     public List<Job> getAllJobs(){
        return jobService.getAllJobs();
     }
     @GetMapping("/{id}")
     public Job getJobById(@PathVariable Long id){
        return jobService.getJobById(id);
     }


}
