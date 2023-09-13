

import React from 'react';
import './App.css';
import SignUp from './components/SignUp';
import Home from './components/Home';
import OpenAccount from './components/OpenAccount';
import "bootstrap/dist/css/bootstrap.min.css"
import Login from './components/Login';
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />}>
          <Route path="login" element={<Login />} />
          <Route path="signup" element={<SignUp />} />
          <Route path="openAccount" element={<OpenAccount />} />
        </Route>
      </Routes>
      </BrowserRouter>
  );
}

export default App