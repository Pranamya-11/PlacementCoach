import { useState } from "react";
import "./ResumeAnalyzer.css";

function ResumeAnalyzer() {
  const [file, setFile] = useState(null);
  const [skills, setSkills] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [questionLoading, setQuestionLoading] = useState(false);
  const [error, setError] = useState("");

  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];

    if (!selectedFile) {
      return;
    }

    const allowedTypes = [
      "application/pdf",
      "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
    ];

    if (!allowedTypes.includes(selectedFile.type)) {
      setError("Please upload a PDF or DOCX file.");
      setFile(null);
      return;
    }

    setFile(selectedFile);
    setSkills([]);
    setQuestions([]);
    setError("");
  };

  // Analyze resume
  const analyzeResume = async () => {
    if (!file) {
      setError("Please choose your resume first.");
      return;
    }

    setLoading(true);
    setError("");
    setSkills([]);
    setQuestions([]);

    try {
      const token = localStorage.getItem("token");

      const formData = new FormData();
      formData.append("file", file);

      const response = await fetch(
        "http://localhost:8080/api/ai/analyze-resume",
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
          },
          body: formData,
        }
      );

      if (!response.ok) {
        throw new Error("Failed to analyze resume");
      }

      const data = await response.json();

      setSkills(data.skills || []);

    } catch (error) {
      console.error(error);
      setError("Could not analyze the resume. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  // Generate questions using detected skills
  const generateQuestions = async () => {
    if (skills.length === 0) {
      setError("Please analyze your resume first.");
      return;
    }

    setQuestionLoading(true);
    setError("");
    setQuestions([]);

    try {
      const token = localStorage.getItem("token");

      const skillsString = skills.join(",");

      const response = await fetch(
        `http://localhost:8080/api/ai/questions?skills=${encodeURIComponent(
          skillsString
        )}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error("Failed to generate questions");
      }

      const data = await response.json();

      setQuestions(data);

    } catch (error) {
      console.error(error);
      setError("Could not generate questions. Please try again.");
    } finally {
      setQuestionLoading(false);
    }
  };

  return (
    <div className="resume-analyzer">

      <h1>AI Resume & Skill Analyzer</h1>

      <p className="subtitle">
        Upload your resume and let AI identify your technical skills.
      </p>

      {/* Upload section */}
      <div className="analyzer-card">

        <label className="upload-button">
          📄 Choose Resume

          <input
            type="file"
            accept=".pdf,.docx"
            onChange={handleFileChange}
          />
        </label>

        {file && (
          <p className="selected-file">
            Selected: <strong>{file.name}</strong>
          </p>
        )}

        <button
          onClick={analyzeResume}
          disabled={loading || !file}
        >
          {loading ? "Analyzing..." : "Analyze Resume"}
        </button>

        {error && (
          <p className="error-message">
            {error}
          </p>
        )}

      </div>

      {/* Skills */}
      {skills.length > 0 && (
        <div className="skills-section">

          <h2>Detected Technical Skills</h2>

          <div className="skills-container">
            {skills.map((skill, index) => (
              <span className="skill-tag" key={index}>
                {skill}
              </span>
            ))}
          </div>

          <button
            className="question-button"
            onClick={generateQuestions}
            disabled={questionLoading}
          >
            {questionLoading
              ? "Generating Questions..."
              : "Generate Technical Questions"}
          </button>

        </div>
      )}

      {/* Questions */}
      {questions.length > 0 && (
        <div className="questions-section">

          <h2>AI Generated Technical Questions</h2>

          {questions.map((item, index) => (
            <div className="question-card" key={index}>

              <div className="question-header">

                <span className="question-number">
                  Question {index + 1}
                </span>

                <span className="difficulty">
                  {item.difficulty}
                </span>

              </div>

              <p className="skill">
                Skill: {item.skill}
              </p>

              <p className="question">
                {item.question}
              </p>

            </div>
          ))}

        </div>
      )}

    </div>
  );
}

export default ResumeAnalyzer;