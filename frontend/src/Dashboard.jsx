import { useEffect, useState } from "react";
import "./Dashboard.css";

function Dashboard() {
  const [dashboard, setDashboard] = useState(null);
  const [readiness, setReadiness] = useState(null);
  const [recommendations, setRecommendations] = useState([]);
  const [recentApplications, setRecentApplications] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const token = localStorage.getItem("token");

      if (!token) {
        setError("Please login again.");
        return;
      }

      const headers = {
        Authorization: `Bearer ${token}`,
      };

      // Dashboard statistics
      const dashboardResponse = await fetch(
        "http://localhost:8080/api/dashboard/me",
        {
          headers,
        }
      );

      if (!dashboardResponse.ok) {
        throw new Error("Failed to load dashboard");
      }

      const dashboardData = await dashboardResponse.json();
      setDashboard(dashboardData);

      // Placement readiness
      const readinessResponse = await fetch(
        "http://localhost:8080/api/readiness/me",
        {
          headers,
        }
      );

      if (readinessResponse.ok) {
        const readinessData = await readinessResponse.json();
        setReadiness(readinessData);
      }

      // Recommendations - still userId=6 for now
      const recommendationResponse = await fetch(
        "http://localhost:8080/api/recommendations",
        {
          headers,
        }
      );

      if (recommendationResponse.ok) {
        const recommendationData =
          await recommendationResponse.json();

        setRecommendations(recommendationData);
      }

      // Recent applications - still userId=6 for now
      const applicationsResponse = await fetch(
        "http://localhost:8080/api/applications/recent",
        {
          headers,
        }
      );

      if (applicationsResponse.ok) {
        const applicationsData =
          await applicationsResponse.json();

        setRecentApplications(applicationsData);
      }

    } catch (error) {
      console.error(error);
      setError("Failed to load dashboard.");
    }
  };

  if (error) {
    return (
      <div className="dashboard">
        <h2>{error}</h2>
      </div>
    );
  }

  if (!dashboard) {
    return (
      <div className="dashboard">
        <h2>Loading dashboard...</h2>
      </div>
    );
  }

  return (
    <div className="dashboard">

      <h1>Dashboard</h1>

      {/* Statistics */}

      <div className="dashboard-grid">

        <div className="dashboard-card">
          <h3>Applied Jobs</h3>
          <p>{dashboard.appliedJobs}</p>
        </div>

        <div className="dashboard-card">
          <h3>Pending</h3>
          <p>{dashboard.pending}</p>
        </div>

        <div className="dashboard-card">
          <h3>Shortlisted</h3>
          <p>{dashboard.shortlisted}</p>
        </div>

        <div className="dashboard-card">
          <h3>Rejected</h3>
          <p>{dashboard.rejected}</p>
        </div>

        <div className="dashboard-card">
          <h3>Total Jobs</h3>
          <p>{dashboard.totalJobs}</p>
        </div>

        <div className="dashboard-card">
          <h3>Active Jobs</h3>
          <p>{dashboard.activeJobs}</p>
        </div>

      </div>

      {/* Placement Readiness */}

      {readiness && (
        <div className="readiness-section">

          <h2>Placement Readiness</h2>

          <div className="readiness-card">

            <div className="readiness-score">
              {readiness.score}%
            </div>

            <h3>{readiness.level}</h3>

            <p>{readiness.message}</p>

            <div className="progress-bar">
              <div
                className="progress-fill"
                style={{
                  width: `${readiness.score}%`,
                }}
              ></div>
            </div>

          </div>

        </div>
      )}

      {/* Recommendations */}

      <div className="recommendations-section">

        <h2>Recommended Jobs</h2>

        {recommendations.length === 0 ? (
          <p>No recommended jobs available.</p>
        ) : (
          <div className="recommendation-grid">

            {recommendations.map((job, index) => (
              <div
                className="recommendation-card"
                key={index}
              >

                <h3>{job.company}</h3>

                <p>{job.role}</p>

                <p>{job.location}</p>

                <strong>
                  {job.matchScore}% Match
                </strong>

              </div>
            ))}

          </div>
        )}

      </div>

      {/* Recent Applications */}

      <div className="applications-section">

        <h2>Recent Applications</h2>

        {recentApplications.length === 0 ? (
          <p>No recent applications.</p>
        ) : (
          <div className="applications-list">

            {recentApplications.map(
              (application, index) => (
                <div
                  className="application-item"
                  key={index}
                >

                  <div>
                    <h3>{application.company}</h3>
                    <p>{application.role}</p>
                  </div>

                  <div>
                    <p>
                      {application.appliedDate}
                    </p>

                    <strong>
                      {application.status}
                    </strong>
                  </div>

                </div>
              )
            )}

          </div>
        )}

      </div>

    </div>
  );
}

export default Dashboard;