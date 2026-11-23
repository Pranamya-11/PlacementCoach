import { useState } from "react";
import "./App.css";
import Profile from "./Profile";
import Applications from "./Applications";
import Dashboard from "./Dashboard";
import Jobs from "./Jobs";
import ResumeAnalyzer from "./ResumeAnalyzer";

function App() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loggedIn, setLoggedIn] = useState(false);
  const [page, setPage] = useState("dashboard");

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(
        "http://localhost:8080/api/auth/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: email,
            password: password,
          }),
        }
      );

      if (!response.ok) {
        throw new Error("Invalid email or password");
      }

      const token = await response.text();

      localStorage.setItem("token", token);

      setLoggedIn(true);
      setPage("dashboard");

    } catch (error) {
      alert(error.message);
    }
  };

  // Show Dashboard / Jobs after login
  if (loggedIn) {
    return (
      <div>

        <nav className="navbar">

          <h2>PlacementCoach</h2>

          <div className="nav-links">

            <button onClick={() => setPage("dashboard")}>
              Dashboard
            </button>

            <button onClick={() => setPage("jobs")}>
              Jobs
            </button>
            <button onClick={() => setPage("applications")}>
              Applications
            </button>

            <button onClick={() => setPage("profile")}>
              Profile
            </button>

            <button onClick={() => setPage("resume")}>
              Resume Analyzer
            </button>

            <button
              onClick={() => {
                localStorage.removeItem("token");
                setLoggedIn(false);
              }}
            >
              Logout
            </button>

          </div>

        </nav>

        {page === "dashboard" && <Dashboard />}

        {page === "jobs" && <Jobs />}
        {page === "applications" && <Applications />}
{page === "profile" && <Profile />}
{page === "resume" && <ResumeAnalyzer />}
      </div>
    );
  }

  // Login page
  return (
    <div className="login-page">

      <div className="login-card">

        <h1>PlacementCoach</h1>

        <p className="subtitle">
          Your personal placement companion
        </p>

        <form onSubmit={handleLogin}>

          <label>Email</label>

          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <label>Password</label>

          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <button type="submit">
            Login
          </button>

        </form>

{/*         <p className="register-text"> */}
{/*           Don't have an account? <span>Register</span> */}
{/*         </p> */}

      </div>

    </div>
  );
}

export default App;