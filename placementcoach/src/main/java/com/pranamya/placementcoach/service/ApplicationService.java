
package com.pranamya.placementcoach.service;

import com.pranamya.placementcoach.exception.DuplicateApplicationException;
import com.pranamya.placementcoach.exception.ResourceNotFoundException;
import com.pranamya.placementcoach.model.Application;
import com.pranamya.placementcoach.model.Job;
import com.pranamya.placementcoach.model.User;
import com.pranamya.placementcoach.repository.ApplicationRepository;
import com.pranamya.placementcoach.repository.JobRepository;

import com.pranamya.placementcoach.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
        @Service
        public class ApplicationService {
            private final ApplicationRepository applicationRepository;
            private final UserRepository  userRepository;
            private final JobRepository jobRepository ;

            public ApplicationService(
                    ApplicationRepository applicationRepository,
                    UserRepository userRepository,
                    JobRepository jobRepository
            ) {
                this.applicationRepository = applicationRepository;
                this.userRepository = userRepository;
                this.jobRepository = jobRepository;
            }
            public Application saveApplication(Application application){
                return applicationRepository.save(application);

            }
            public List<Application> getAllApplications(){
                return applicationRepository.findAll();
            }
            public Application getApplicationById(Long id){
                return applicationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Application not found"));
            }
            public Application updateApplicationStatus(Long id, String status) {

                Application application = applicationRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

                application.setStatus(status);

                return applicationRepository.save(application);
            }
            public Application applyForJob(Long jobId, Long userId) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                Job job = jobRepository.findById(jobId)
                        .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
                if (applicationRepository.existsByUserAndJob(user, job)) {
                    throw new DuplicateApplicationException("You have already applied for this job");
                }
                Application application = new Application();
                application.setUser(user);
                application.setJob(job);
                application.setStatus("Applied");
                application.setAppliedDate(LocalDate.now());
                return applicationRepository.save(application);
            }


        }





