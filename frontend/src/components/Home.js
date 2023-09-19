import React, { useEffect } from "react";
import "../styles/Home.css";
import { Outlet, Link, useNavigate } from "react-router-dom";
import Nav from "./Nav";
import Cookies from "universal-cookie";

const Home = () => {
  const cookies=new Cookies();
  const navigate=useNavigate();
  
  return (
    <>
      <Nav />
      {
        //   <div className="home-div">
        //     <h3>
        //       <center>Welcome to Bank With Fargo!</center>
        //     </h3>
        //   </div>
      }
      <Outlet />
    </>
  );
};

export default Home;
