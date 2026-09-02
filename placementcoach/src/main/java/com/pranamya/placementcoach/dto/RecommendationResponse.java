package com.pranamya.placementcoach.dto;

public class RecommendationResponse {
    private String company;
    private String role;
    private String location;
    private int matchScore;

    public RecommendationResponse(
            String company,
            String role,
            String location,
            int matchScore
    ) {
        this.company = company;
        this.role = role;
        this.location = location;
        this.matchScore = matchScore;
    }

    public String getRole() {
        return role;
    }



    public String getLocation() {
        return location;
    }


    public int getMatchScore() {
        return matchScore;
    }



    public String getCompany() {
        return company;
    }


}
