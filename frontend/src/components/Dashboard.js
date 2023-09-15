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
      <div className="cards-body">
        <Card
          className="dashboard-card card-title"
          onClick={handleOnClickTransactions}
        >
          <Card.Body>
            <center>
              <a href="/transactions">View Transactions</a>
            </center>
          </Card.Body>
        </Card>

        <Card
          className="dashboard-card card-title"
          onClick={handleOnClickBalance}
        >
          <Card.Body>
            <center>Check Balance</center>
          </Card.Body>
        </Card>

        <Card
          className="dashboard-card card-title"
          onClick={handleOnClickTransactions}
        >
          <Card.Body>
            <center>
              <i class="pi pi-check"></i>
              <a href="/openAccount">Open a Savings Account</a>
            </center>
          </Card.Body>
        </Card>

        {show && <ViewBalanceModal show={show} setShow={setShow} />}
      </div>
    </>
  );
}

export default Dashboard;
