const API_URL = "http://localhost:8085/users";
console.log(localStorage.getItem('user'));
// Function to set the global user and save it in localStorage
export const setUser = (userData) => {
  localStorage.setItem('user', JSON.stringify(userData)); // Save user data in localStorage
};

// Function to get the current user from localStorage
export const getUser = () => {
  const user = localStorage.getItem('user');
  return user ? JSON.parse(user) : null; // Return the parsed user object or null if not found
};

// Function to create user- Sign Up
export const createUser = async (userData) => {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    });

    if (response.status === 409) { 
        const errorMessage = await response.json();
        throw new Error(errorMessage.message || "User with this email already exists!"); 
      }
    else if (!response.ok) {
      const errorMessage = await response.json();
      throw new Error(errorMessage.message || "Unknown error occured, please try again after sometime!!");
    }

    return await response.json(); 
  } catch (error) {
    throw error; // Propagate error to be handled in the component
  }
};

// Function to check if user exists by email and set the user - LOGIN
export const checkUserExists = async (email,userName) => {
  try {
    const response = await fetch(`${API_URL}/exist/${email}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      const errorMessage = await response.json();
      throw new Error(errorMessage.message || "Something went wrong, please try again later!");
    }

    const user = await response.json(); // Get user details
    if(userName !== user.userName)
    {
      throw new Error("Username not correct!!");
    }
    setUser(user); // Save the user in local storage
    return user; 
  } catch (error) {
    throw error; // Propagate error
  }
};


// Function to log out the user and clear localStorage
export const logout = () => {
  localStorage.removeItem('user'); // Clear user data from localStorage
};
// Function to get all users
export const getAllUsers = async () => {
  try {
    const response = await fetch(API_URL, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      const errorMessage = await response.json();
      throw new Error(errorMessage.message || "Failed to fetch users");
    }
    return await response.json(); // Return the list of users
  } catch (error) {
    throw error; // Propagate error
  }
};

// Function to update a user
export const updateUser = async (userId, userData) => {
  try {
    const response = await fetch(`${API_URL}/${userId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    });

    if (!response.ok) {
      const errorMessage = await response.json();
      throw new Error(errorMessage.message || "Failed to update user");
    }

    return await response.json(); // Return the updated user object
  } catch (error) {
    throw error; // Propagate error
  }
};

// Function to delete a user
export const deleteUser = async (userId) => {
  try {
    const response = await fetch(`${API_URL}/${userId}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      const errorMessage = await response.json();
      throw new Error(errorMessage.message || "Failed to delete user");
    }

    return await response.json(); // Return a success message or empty response
  } catch (error) {
    throw error; // Propagate error
  }
};
