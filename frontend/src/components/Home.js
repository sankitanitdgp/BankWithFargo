import React, { useEffect, useInsertionEffect } from "react";
import "../styles/Home.css";
import { Outlet, Link, useNavigate } from "react-router-dom";
import Nav from "./Nav";
import Body from "./Body";
import Cookies from "universal-cookie";

const Home = () => {
  const cookies=new Cookies();
  const navigate=useNavigate();
  
  return (
    <>
      <Body/>
    </>
  );
};

export default Home;
