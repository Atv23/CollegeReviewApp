const API_URL = "http://localhost:8085/auth/login";

export const oktaLogin = async (token) => {
  try {
    const response = await fetch(API_URL, {
      method: "GET", // Assuming you're using GET to fetch the token
      headers: {
        Authorization: `Bearer ${token}`, // Send the token as Authorization header
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      const errorMessage = await response.json();
      throw new Error(errorMessage.message || "Login failed, please try again.");
    }

    return await response.json(); // Return the AuthResponse object
  } catch (error) {
    throw error; // Propagate error
  }
};