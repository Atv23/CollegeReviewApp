import React, { useEffect } from "react";
import axios from "axios";

const Redirect = () => {
  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await axios.get("http://localhost:8085/auth/user-info");
        const { accessToken, userId, userName } = response.data;

        // Create a user object
        const user = {
          accessToken,
          userEmail: userId,
          userName,
        };

        // Store the user object in localStorage
        localStorage.setItem("user", JSON.stringify(user));

        // Redirect to the dashboard
        window.location.href = "/login"; // or use React Router's useHistory hook to navigate
      } catch (error) {
        console.error("Failed to fetch user info:", error);
        // Handle error (e.g., show a message to the user)
      }
    };

    fetchUserInfo();
  }, []);

  return (
    <div>
      <h2>Redirecting...</h2>
    </div>
  );
};

export default Redirect;
