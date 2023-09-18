import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import "../styles/Dashboard.css";
import { Link, useNavigate } from "react-router-dom";
import ViewBalanceModal from "./ViewBalanceModal";
import Cookies from "universal-cookie";

function Dashboard() {
  const [show, setShow] = useState(false);
  const cookies=new Cookies();
  const navigate=useNavigate();
  const handleOnClickTransactions = () => {};

  const handleOnClickBalance = () => {
    console.log("here");
    setShow(true);
  };

  useEffect(()=>{
    if(!cookies.get("token")){
      navigate("/login")
    }
  })

  return (
    <>
      <div>
        <Link className="card-link" to="/openAccount">
          <Card
            className="dashboard-card card-img-"
            onClick={handleOnClickTransactions}
          >
            <Card.Body>Open new account</Card.Body>
          </Card>
        </Link>

        <div className="card-link">
        <Card className="dashboard-card" onClick={handleOnClickBalance}>
          <Card.Body>Check Balance</Card.Body>
        </Card>
        </div>

        <Link className="card-link" to="/transactions">
        <Card
            className="dashboard-card card-img-"
            onClick={handleOnClickTransactions}
          >
            <Card.Body>View Transactions</Card.Body>
          </Card>
        </Link>
        {show && <ViewBalanceModal show={show} setShow={setShow} />}
      </div>
    </>
  );
}

export default Dashboard;
