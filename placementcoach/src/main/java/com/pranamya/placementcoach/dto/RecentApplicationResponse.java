package com.pranamya.placementcoach.dto;

import java.time.LocalDate;

public class RecentApplicationResponse {

    private String company;
    private String role;
    private String status;
    private LocalDate appliedDate;

    public RecentApplicationResponse(
            String company,
            String role,
            String status,
            LocalDate appliedDate
    ) {
        this.company = company;
        this.role = role;
        this.status = status;
        this.appliedDate = appliedDate;
    }

    public String getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }
}