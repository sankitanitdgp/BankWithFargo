import React from 'react'

import { Outlet, Link } from "react-router-dom";

const Home = () => {
  return (
    <>
      <nav>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/login">Login</Link>
          </li>
          <li>
            <Link to="/signup">Sign up</Link>
          </li>
          <li>
            <Link to="/openAccount">Open account</Link>
          </li>
        </ul>
      </nav>
        Home Page
      <Outlet />
    </>
  )
};


export default Home;