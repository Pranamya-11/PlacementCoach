
import { useEffect, useState } from "react";
import "./Applications.css";

function Applications() {
  const [applications, setApplications] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchApplications = async () => {
      try {
        const token = localStorage.getItem("token");

        const response = await fetch(
          "http://localhost:8080/api/applications/recent",
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) {
          throw new Error("Failed to load applications");
        }

        const data = await response.json();

        setApplications(data);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchApplications();
  }, []);

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div className="applications-page">
      <div className="applications-header">
        <h1>My Applications</h1>
        <p>Track the jobs you have applied for.</p>
      </div>

      <main className="applications-container">
        {applications.length === 0 ? (
          <div className="empty-applications">
            <h2>No applications yet</h2>
            <p>Apply to jobs to start tracking your applications.</p>
          </div>
        ) : (
          applications.map((application) => (
            <div
              className="application-full-card"
              key={application.id}
            >
              <div className="application-details">
                <h2>{application.company}</h2>
                <h3>{application.role}</h3>
                <p>📅 Applied: {application.appliedDate}</p>
              </div>

              <div className="status-section">
                <span className="status-label">Status</span>
                <span className="status">
                  {application.status}
                </span>
              </div>
            </div>
          ))
        )}
      </main>
    </div>
  );
}

export default Applications;
