// Redirect.js
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Redirect = () => {
    useEffect(() => {
        const handleTokenResponse = async () => {
          const response = await fetch('http://localhost:8085/auth/login', {
            method: 'GET',
            credentials: 'include', // Include cookies, if any
          });
          if (response.ok) {
            const { accessToken, userId } = await response.json();
            localStorage.setItem('access_token', accessToken);
            localStorage.setItem('user_email', userId); // Assuming userId is the email
            // Redirect to dashboard
            window.location.href = '/dashboard';
          } else {
            console.error("Failed to fetch token");
            // Handle error appropriately
          }
        };
    
        handleTokenResponse();
      }, []); // Empty dependency array to run only once on mount
    
      return <div>Loading...</div>; // Optional loading message while waiting for the token
    };

export default Redirect;
