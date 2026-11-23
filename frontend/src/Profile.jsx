
import { useEffect, useState } from "react";
import "./Profile.css";

function Profile() {

    const [profile, setProfile] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {

        const fetchProfile = async () => {

            try {
                const token = localStorage.getItem("token");

                const response = await fetch(
                    "http://localhost:8080/api/profile/me",
                    {
                        method: "GET",
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );

                if (!response.ok) {
                    throw new Error("Failed to load profile");
                }

                const data = await response.json();

                setProfile(data);

            } catch (error) {
                setError(error.message);
            }
        };

        fetchProfile();

    }, []);

    if (error) {
        return <h2>{error}</h2>;
    }

    if (!profile) {
        return <h2>Loading profile...</h2>;
    }

    return (
        <div className="profile-page">

            <div className="profile-header">

                <h1>My Profile</h1>

                <p>
                    Keep your placement information updated.
                </p>

            </div>

            <main className="profile-container">

                <div className="profile-card">

                    <div className="profile-row">
                        <span>USN</span>
                        <strong>{profile.usn}</strong>
                    </div>

                    <div className="profile-row">
                        <span>Branch</span>
                        <strong>{profile.branch}</strong>
                    </div>

                    <div className="profile-row">
                        <span>CGPA</span>
                        <strong>{profile.cgpa}</strong>
                    </div>

                    <div className="profile-row">
                        <span>Skills</span>
                        <strong>{profile.skills}</strong>
                    </div>

                    <div className="profile-row">
                        <span>LeetCode Problems</span>
                        <strong>{profile.leetcodeCount}</strong>
                    </div>

                    <div className="profile-row">
                        <span>Resume</span>

                        {profile.resumeLink ? (
                            <a
                                href={profile.resumeLink}
                                target="_blank"
                                rel="noreferrer"
                            >
                                View Resume
                            </a>
                        ) : (
                            <span>No resume uploaded</span>
                        )}

                    </div>

                </div>

            </main>

        </div>
    );
}

export default Profile;
