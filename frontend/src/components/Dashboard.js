import React, { useEffect, useState } from "react";
import { Card, Container, Row, Col } from "react-bootstrap";
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
		} else if (cookies.get("role") == "admin") {
			navigate("/adminDashboard");
		}
	});

	return (
		<Container fluid>
			<Row>
				<Col>
					<Link to="/openAccount" style={{ color: "black" }}>
						<div className="card-div">
							<div className="card-icon-div">
								<i class="fas fa-university"></i>
							</div>
							<div className="card-title">Open a new account</div>
						</div>
					</Link>
				</Col>
				<Col>
					<div className="card-div" onClick={handleOnClickBalance}>
						<div className="card-icon-div">
							<i class="fas fa-money-check-alt"></i>
						</div>
						<div className="card-title">Check Account Balance</div>
					</div>
				</Col>
				<Col>
					<Link to="/transactions" style={{ color: "black" }}>
						<div className="card-div">
							<div className="card-icon-div">
								<i class="fas fa-wallet"></i>
							</div>
							<div className="card-title">View Transactions</div>
						</div>
					</Link>
				</Col>
			</Row>
			<Row>
				<Col>
					<div className="card-div" onClick={handleOnClickDeposit}>
						<div className="card-icon-div">
							<i class="fas fa-coins"></i>
						</div>

						<div className="card-title">Deposit Money</div>
					</div>
				</Col>
				<Col>
					<div className="card-div" onClick={handleOnClickWithdraw}>
						<div className="card-icon-div">
							<i class="fas fa-rupee-sign"></i>
						</div>
						<div className="card-title">Withdraw Money</div>
					</div>
				</Col>
				<Col>
					<div className="card-div" onClick={handleOnClickTransfer}>
						<div className="card-icon-div">
							<i class="fas fa-exchange-alt"></i>
						</div>
						<div className="card-title">Transfer Money</div>
					</div>
				</Col>
			</Row>
			{/* <Card className="dashboard-card card-img- card-link">
				<Link className="" to="/openAccount">
					<Card.Body>Open new account</Card.Body>
				</Link>
			</Card>

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

			<Card
				className="dashboard-card card-img- card-link"
				onClick={handleOnClickDeposit}
			>
				<Link className="deposit-money-card card-link">
					<Card.Body>Deposit Money</Card.Body>
				</Link>
			</Card>

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
			</Link> */}
			{showViewBalance && (
				<ViewBalanceModal show={showViewBalance} setShow={setShowViewBalance} />
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
		</Container>
	);
}

export default Dashboard;
