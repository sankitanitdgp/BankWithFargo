import React from "react";
import "../styles/Home.css";
import { Outlet, Link } from "react-router-dom";
import Nav from "./Nav";

const Home = () => {
  return (
    <>
      <Nav />
      {/* <div className="home-div">
        <h3>Welcome to Bank With Fargo!</h3>
      </div> */}
      <Outlet />
    </>
  );
};

export default Home;
