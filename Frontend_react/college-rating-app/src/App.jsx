import logo from "./logo.svg";
import "./App.css";
import Home from "./components/home/Home";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Signup from "./components/signup/Signup";
import Login from "./components/login/Login";
import Colleges from "./components/colleges/Colleges";
import Navbar from "./components/navbar/Navbar";
import Dashboard from "./components/dashboard/Dashboard";
import Redirect from "./components/redirect/Redirect";

function App() {
  return (
    <Router>
      <div>
        <Navbar />
        <div className="content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/login" element={<Login />} />
            <Route path="/colleges" element={<Colleges />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/redirect" element={<Redirect />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
