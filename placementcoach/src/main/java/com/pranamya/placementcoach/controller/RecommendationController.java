package com.pranamya.placementcoach.controller;

import com.pranamya.placementcoach.dto.RecommendationResponse;
import com.pranamya.placementcoach.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService
    ) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<RecommendationResponse> getRecommendations(
            @RequestParam Long userId
    ) {
        return recommendationService.getRecommendations(userId);
    }
}