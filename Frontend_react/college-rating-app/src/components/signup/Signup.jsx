// src/components/Signup.jsx
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Signup.css";
import { RiUserAddFill } from "react-icons/ri";
import { createUser } from "../../services/userService";

function Signup() {
  const [formData, setFormData] = useState({
    userName: "",
    userEmail: "",
    userGender: "",
    userDescription: "",
  });

  const navigate = useNavigate(); // To redirect after signup
  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
    setErrorMessage(""); // Clear error message on input change
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createUser(formData); // This sets the user globally
      navigate("/login"); // Redirect on success
    } catch (error) {
      setErrorMessage(`Signup failed: ${error.message}`); // Set error message state
    }
  };
  const handleReset = () => {
    setFormData({
      userName: "",
      userEmail: "",
      userGender: "",
      userDescription: "",
    });
    setErrorMessage(""); // Clear error message on reset
  };
  return (
    <div className="signup-container">
      <div className="heading">
        <RiUserAddFill className="signup-icon" /> 
        <h2>Sign Up</h2>
      </div>
      {errorMessage && <div className="error-message">{errorMessage}</div>}
      <form onSubmit={handleSubmit}>
        <label htmlFor="userName">Username:</label>
        <input
          type="text"
          id="userName"
          name="userName"
          value={formData.userName}
          onChange={handleChange}
          required
        />

        <label htmlFor="userEmail">Email:</label>
        <input
          type="email"
          id="userEmail"
          name="userEmail"
          value={formData.userEmail}
          onChange={handleChange}
          required
        />

        <label htmlFor="userGender">Gender:</label>
        <select
          id="userGender"
          name="userGender"
          value={formData.userGender}
          onChange={handleChange}
          required
        >
          <option value="">Select</option>
          <option value="male">Male</option>
          <option value="female">Female</option>
          <option value="other">Other</option>
        </select>

        <label htmlFor="userDescription">Description:</label>
        <textarea
          id="userDescription"
          name="userDescription"
          value={formData.userDescription}
          onChange={handleChange}
          required
        />
        <div className="btn-container">
          <button type="submit" className="signup">
            Sign Up
          </button>
          <button type="button" className="reset" onClick={handleReset}>
            Reset
          </button>
        </div>
      </form>
      <p className = "last-msg text-primary">
        Already have an account? <Link to="/login" className=" btn btn-sm btn-warning">Login</Link>
      </p>
    </div>
  );
}

export default Signup;
