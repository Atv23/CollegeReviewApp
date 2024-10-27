import React, { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  FaHome,
  FaUniversity,
  FaSignInAlt,
  FaUserPlus,
  FaBars,
  FaTimes,
  FaUser,
  FaSignOutAlt,
} from "react-icons/fa"; // Added FaTimes for closing icon
import "./Navbar.css";
import { getUser, logout } from "../../services/userService";
import { RiShutDownLine } from "react-icons/ri";

const Navbar = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [user, setUser] = useState(null);

  useEffect(() => {
    // Check for a logged-in user from localStorage on component mount
    const loggedUser = getUser();
    setUser(loggedUser); // Set user state if found
  }, []);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  const handleLogout = () => {
    logout(); // Clear user data from localStorage
    setUser(null); // Clear the user state
  };
  return (
    <div>
      <nav>
        <Link to="/" className="app-name">
          College Review App
        </Link>

        <div className="menu-icon" onClick={toggleSidebar}>
          {isSidebarOpen ? (
            <FaTimes style={{ fontSize: "1.5rem", color: "white" }} /> // Show 'X' icon when sidebar is open
          ) : (
            <FaBars style={{ fontSize: "1.5rem", color: "white" }} /> // Show menu icon when sidebar is closed
          )}
        </div>
        <ul className="nav-links">
          <li>
            <Link to="/">
              <FaHome />
              <span className="tooltip">Home</span>
            </Link>
          </li>
          <li>
            <Link to="/colleges">
              <FaUniversity />
              <span className="tooltip">View Colleges</span>
            </Link>
          </li>
          {/* Conditional rendering based on whether the user is logged in */}
          {user ? (
            <>
              <li>
                <Link to="/dashboard">
                  <FaUser />
                  <span className="tooltip">{user.userName}</span>{" "}
                  {/* Show username on hover */}
                </Link>
              </li>
              <li>
                <Link to="/login" onClick={handleLogout}>
                <RiShutDownLine />
                  <span className="tooltip">Logout</span>
                </Link>
              </li>
            </>
          ) : (
            <>
              <li>
                <Link to="/login">
                  <FaSignInAlt />
                  <span className="tooltip">Login</span>
                </Link>
              </li>
              <li>
                <Link to="/signup">
                  <FaUserPlus />
                  <span className="tooltip">Sign Up</span>
                </Link>
              </li>
            </>
          )}
        </ul>
      </nav>

      {/* Sidebar for smaller screens */}
      <div className={`sidebar ${isSidebarOpen ? "active" : ""}`}>
        <ul className="sidebar-links">
          <li>
            <Link to="/" onClick={toggleSidebar}>
              <FaHome /> <span>Home</span>
            </Link>
          </li>
          <li>
            <Link to="/colleges" onClick={toggleSidebar}>
              <FaUniversity /> <span>View Colleges</span>
            </Link>
          </li>
          {user ? (
            <>
              <li>
                <Link to="/dashboard" onClick={toggleSidebar}>
                  <FaUser /> <span>{user.userName}</span> {/* Show username */}
                </Link>
              </li>
              <li>
                <Link
                  to="/login"
                  onClick={() => {
                    handleLogout();
                    toggleSidebar();
                  }}
                >
                  <RiShutDownLine /> <span>Logout</span>
                </Link>
              </li>
            </>
          ) : (
            <>
              <li>
                <Link to="/login" onClick={toggleSidebar}>
                  <FaSignInAlt /> <span>Login</span>
                </Link>
              </li>
              <li>
                <Link to="/signup" onClick={toggleSidebar}>
                  <FaUserPlus /> <span>Signup</span>
                </Link>
              </li>
            </>
          )}
        </ul>
      </div>
    </div>
  );
};

export default Navbar;
