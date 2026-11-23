
package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.dto.RecommendationResponse;
import com.pranamya.placementcoach.service.RecommendationService;
import com.pranamya.placementcoach.repository.UserRepository;
import com.pranamya.placementcoach.model.User;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final UserRepository userRepository;

    public RecommendationController(
            RecommendationService recommendationService,
            UserRepository userRepository
    ) {
        this.recommendationService = recommendationService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<RecommendationResponse> getRecommendations(
            Authentication authentication
    ) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        return recommendationService.getRecommendations(user.getId());
    }
}
