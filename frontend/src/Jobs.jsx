import { useEffect, useState } from "react";
import "./Jobs.css";

function Jobs() {
  const [jobs, setJobs] = useState([]);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const userId = 6;

  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await fetch(
        "http://localhost:8080/api/jobs",
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Failed to load jobs");
      }

      const data = await response.json();

      setJobs(data);
    } catch (error) {
      setError(error.message);
    }
  };

  const applyForJob = async (jobId) => {
    try {
      const token = localStorage.getItem("token");

      const response = await fetch(
        `http://localhost:8080/api/applications/jobs/${jobId}/apply?userId=${userId}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(
          errorData.message || "Failed to apply"
        );
      }

      setMessage("Application submitted successfully! 🎉");

      // Refresh jobs after applying
      fetchJobs();

    } catch (error) {
      setMessage(error.message);
    }
  };

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div className="jobs-page">

      <header className="jobs-header">
        <h1>PlacementCoach</h1>

        <h2>Available Jobs</h2>

        <p>
          Find opportunities that match your skills.
        </p>
      </header>

      {message && (
        <div className="job-message">
          {message}
        </div>
      )}

      <main className="jobs-container">

        {jobs.length === 0 ? (

          <p>No jobs available.</p>

        ) : (

          jobs.map((job) => (

            <div className="job-card" key={job.id}>

              <div className="job-info">

                <h2>{job.company}</h2>

                <h3>{job.role}</h3>

                <p>
                  📍 {job.location}
                </p>

                <p>
                  💰 {job.salaryPackage} LPA
                </p>

                <p>
                  🎓 Minimum CGPA: {job.minimumCgpa}
                </p>

                <p>
                  🛠 Skills: {job.requiredSkills}
                </p>

                <p>
                  📅 Deadline: {job.deadline}
                </p>

                <p>
                  {job.description}
                </p>

              </div>

              <button
                className="apply-button"
                onClick={() => applyForJob(job.id)}
              >
                Apply
              </button>

            </div>

          ))

        )}

      </main>

    </div>
  );
}

export default Jobs;