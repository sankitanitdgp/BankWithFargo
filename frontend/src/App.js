import React from "react";
import "./App.css";
import SignUp from "./components/SignUp";
import Home from "./components/Home";
import OpenAccount from "./components/OpenAccount";
import "bootstrap/dist/css/bootstrap.min.css";
import Login from "./components/Login";
import TransactionHistory from "./components/TransactionHistory";
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Admin from "./components/Admin";
import Logout from "./components/Logout";

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />}>
          <Route path="login" element={<Login/>} />
          <Route path="signup" element={<SignUp />} />
          <Route path="openAccount" element={<OpenAccount />} />
          <Route path="dashboard" element={<Dashboard/>} />
          <Route path="adminDashboard" element={<Admin/>} />
          <Route path="transactions" element={<TransactionHistory />} />
          <Route path="logout" element={<Logout/>} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
