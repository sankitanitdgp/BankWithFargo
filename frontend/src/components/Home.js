import React from 'react'

import { Outlet, Link } from "react-router-dom";
import Nav from './Nav';

const Home = () => {
  return (
    <>
    <Nav />
    <Outlet />
    </>
  )
};


export default Home;