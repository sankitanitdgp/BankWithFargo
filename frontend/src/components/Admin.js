import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import "../styles/Dashboard.css";
import { Link, useNavigate } from "react-router-dom";
import ViewBalanceModal from "./ViewBalanceModal";
import Cookies from "universal-cookie";
import DepositMoneyModal from "./DepositMoneyModal";

function AdminDashboard() {

  const cookies = new Cookies();
  const navigate = useNavigate();

  useEffect(() => {
    if (!cookies.get("token")) {
      navigate("/login");
    }
  });

  return (
    <>
      <div>
        <Link className="card-link" to="/openAccount">
          <Card className="dashboard-card card-img-">
            <Card.Body>Create account</Card.Body>
          </Card>
        </Link>

        <Link className="card-link">
          <Card className="dashboard-card" >
            <Card.Body>Search User</Card.Body>
          </Card>
        </Link>

        <Link className="card-link" to="/transactions">
          <Card className="dashboard-card card-img-">
            <Card.Body>View User Transactions</Card.Body>
          </Card>
        </Link>


        
      </div>
    </>
  );
}

export default AdminDashboard;
