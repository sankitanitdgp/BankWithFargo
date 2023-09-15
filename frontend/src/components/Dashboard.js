import React, { useState } from "react";
import { Card } from "react-bootstrap";
import "../styles/Dashboard.css";
import { Link } from "react-router-dom";
import ViewBalanceModal from "./ViewBalanceModal";

function Dashboard() {
  const [show, setShow] = useState(false);
  const handleOnClickTransactions = () => {};

  const handleOnClickBalance = () => {
    console.log("here");
    setShow(true);
  };

  return (
    <>
      <div>
        <Link to="/transactions">
          <Card className="dashboard-card" onClick={handleOnClickTransactions}>
            <Card.Body>View Transactions</Card.Body>
          </Card>
        </Link>
        <Card className="dashboard-card" onClick={handleOnClickBalance}>
          <Card.Body>Check Balance</Card.Body>
        </Card>
        <Link to="/openAccount">
          <Card className="dashboard-card" onClick={handleOnClickTransactions}>
            <Card.Body>Open a Savings Accounts</Card.Body>
          </Card>
        </Link>

        {show && <ViewBalanceModal show={show} setShow={setShow} />}
      </div>
    </>
  );
}

export default Dashboard;
