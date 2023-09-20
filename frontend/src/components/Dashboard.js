import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import "../styles/Dashboard.css";
import { Link, useNavigate } from "react-router-dom";
import ViewBalanceModal from "./ViewBalanceModal";
import Cookies from "universal-cookie";
import DepositMoneyModal from "./DepositMoneyModal";
import WithdrawMoneyModal from "./WithdrawMoneyModal";
import TransferMoneyModal from "./TransferMoneyModal";

function Dashboard() {
  const [showViewBalance, setShowViewBalance] = useState(false);
  const [showDepositMoney, setShowDepositMoney] = useState(false);
  const [showWithdrawMoney, setShowWithdrawMoney] = useState(false);
  const [showTransferMoney, setShowTransferMoney] = useState(false);
  const cookies = new Cookies();
  const navigate = useNavigate();

  const handleOnClickBalance = () => {
    console.log("here");
    setShowViewBalance(true);
  };

  const handleOnClickDeposit = () => {
    console.log("here");
    setShowDepositMoney(true);
  };

  const handleOnClickWithdraw = () => {
    setShowWithdrawMoney(true);
  };

  const handleOnClickTransfer = () => {
    setShowTransferMoney(true);
  };

  useEffect(() => {
    if (!cookies.get("token")) {
      navigate("/login");
    }else if(cookies.get("role")=="admin"){
      navigate("/adminDashboard")
    }
  });

  return (
    <>
      <div>
        <Link className="card-link" to="/openAccount">
          <Card className="dashboard-card card-img-">
            <Card.Body>Open new account</Card.Body>
          </Card>
        </Link>

        <Link className="card-link">
          <Card className="dashboard-card" onClick={handleOnClickBalance}>
            <Card.Body>Check Balance</Card.Body>
          </Card>
        </Link>

        <Link className="card-link" to="/transactions">
          <Card className="dashboard-card card-img-">
            <Card.Body>View Transactions</Card.Body>
          </Card>
        </Link>

        <Link className="deposit-money-card card-link">
          <Card
            className="dashboard-card card-img-"
            onClick={handleOnClickDeposit}
          >
            <Card.Body>Deposit Money</Card.Body>
          </Card>
        </Link>

        <Link className="card-link">
          <Card
            className="dashboard-card card-img-"
            onClick={handleOnClickWithdraw}
          >
            <Card.Body>Withdraw Money</Card.Body>
          </Card>
        </Link>

        <Link className="card-link">
          <Card
            className="dashboard-card card-img-"
            onClick={handleOnClickTransfer}
          >
            <Card.Body>Transfer Money</Card.Body>
          </Card>
        </Link>
        {showViewBalance && (
          <ViewBalanceModal
            show={showViewBalance}
            setShow={setShowViewBalance}
          />
        )}
        {showDepositMoney && (
          <DepositMoneyModal
            show={showDepositMoney}
            setShow={setShowDepositMoney}
          />
        )}
        {showWithdrawMoney && (
          <WithdrawMoneyModal
            show={showWithdrawMoney}
            setShow={setShowWithdrawMoney}
          />
        )}
        {showTransferMoney && (
          <TransferMoneyModal
            show={showTransferMoney}
            setShow={setShowTransferMoney}
          />
        )}
      </div>
    </>
  );
}

export default Dashboard;
