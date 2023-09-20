import React, { useEffect } from 'react'
import { useNavigate } from 'react-router';
import Cookies from "universal-cookie";

function Logout() {
    const cookies = new Cookies();
    const navigate=useNavigate();
    useEffect(()=>{
        cookies.remove("token");
        cookies.remove("role");
        navigate("/");
    })
  return (
    <div>Logout</div>
  )
}

export default Logout;