package com.pranamya.placementcoach.dto;

public class DashboardResponse {
    private long appliedJobs;
    private long pending;
    private long shortlisted;
    private long rejected;
    private long totalJobs;
    private long activeJobs;

    public DashboardResponse(long appliedJobs, long pending, long shortlisted, long rejected, long totalJobs, long activeJobs)
        {
            this.appliedJobs = appliedJobs;
            this.pending = pending;
            this.shortlisted = shortlisted;
            this.rejected = rejected;
            this.totalJobs = totalJobs;
            this.activeJobs = activeJobs;
        }


        public long getAppliedJobs () {
            return appliedJobs;
        }


        public long getPending () {
            return pending;
        }


        public long getShortlisted () {
            return shortlisted;
        }


        public long getRejected () {
            return rejected;
        }

        public long getActiveJobs () {
            return activeJobs;
        }

        public long getTotalJobs () {
            return totalJobs;
        }
}
