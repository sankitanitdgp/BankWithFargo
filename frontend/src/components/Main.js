import React, { useEffect, useInsertionEffect } from "react";
import "../styles/Home.css";
import { Outlet, Link, useNavigate } from "react-router-dom";
import Nav from "./Nav";
import Body from "./Body";
import Cookies from "universal-cookie";

const Main = () => {
  const cookies=new Cookies();
  const navigate=useNavigate();
  useEffect(()=>{
    if(cookies.get("token"))
    navigate("/dashboard")
    else{
        navigate("/home")
    }
  },[])
  return (
    <>
      <Nav />
 
      <Outlet />
    </>
  );
};

export default Main;
