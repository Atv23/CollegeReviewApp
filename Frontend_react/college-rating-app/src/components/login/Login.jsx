import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./Login.css";
import { checkUserExists, setUser } from "../../services/userService";
import { GiUnlocking } from "react-icons/gi";

const Login = () => {
  const [formData, setFormData] = useState({
    userEmail: "",
    userName: "",
  });
  const [errorMessage, setErrorMessage] = useState("");
  const [userExists, setUserExists] = useState(false);

  // Check if user exists in localStorage on component mount
  useEffect(() => {
    const user = localStorage.getItem("user");
    if (user) {
      setUserExists(true); // Set to true if user exists
    }
  }, []);
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const user = await checkUserExists(formData.userEmail, formData.userName); // Check if user exists
      console.log("User found:", user);
      setUser(user); // Set global user if successful
      window.location.href = "/dashboard"; // Redirect to dashboard with reload
    } catch (error) {
      console.error("Error:", error);
      setErrorMessage(`Login failed: ${error.message}`);
    }
  };

  return (
    <div>
      <div className="login-container">
        <div className="heading">
          <GiUnlocking className="login-icon" />
          <h2>Login</h2>
        </div>

        {errorMessage && <div className="error-message">{errorMessage}</div>}
        <form onSubmit={handleSubmit}>
          <label htmlFor="userName">Username:</label>
          <input
            type="text"
            id="userName"
            name="userName"
            value={formData.userName}
            onChange={(e) =>
              setFormData({ ...formData, userName: e.target.value })
            }
            required
          />

          <label htmlFor="userEmail">Email:</label>
          <input
            type="email"
            id="userEmail"
            name="userEmail"
            value={formData.userEmail}
            onChange={(e) =>
              setFormData({ ...formData, userEmail: e.target.value })
            }
            required
          />

          <button
            type="submit"
            disabled={!userExists} // Disable if user exists
            className={userExists ? "" : "btn btn-danger"}
          >
            Login
          </button>
        </form>
        <p className="last-msg text-primary">
          Don't have an account?{" "}
          <Link to="/signup" className="btn btn-sm btn-warning">
            Sign Up
          </Link>
        </p>
      </div>
      <div className="okta-login">
        <h2>Login from Okta: </h2>
        <a
          href="http://localhost:8085/auth/login"
          target="_blank"
          className={`btn ${userExists ? "btn-danger" : "btn-primary"} ms-3`}
          onClick={(e) => {
            if (userExists) {
              e.preventDefault(); // Prevent the click action if user exists
            }
          }}
        >
          Login
        </a>
      </div>
    </div>
  );
};

export default Login;
