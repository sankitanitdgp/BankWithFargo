import React, { Component, useEffect, useState } from "react";
import "../styles/TransactionHistory.css";
import txn from "../utils/txn";
import Cookies from "universal-cookie";
import { useNavigate } from "react-router";
import {
	Dropdown,
	Button,
	DropdownButton,
	Form,
	Spinner,
} from "react-bootstrap";
import { AccountService } from "../service/AccountService";
import { TransactionHistoryService } from "../service/TransactionHistoryService";

function TransactionHistory() {
	const [transactions, setTransactions] = useState([]);
	const [accounts, setAccounts] = useState([1, 2, 3]);
	const [selectedAcc, setSelectedAcc] = useState("");
	const [mpin, setMpin] = useState("");
	const [error, setError] = useState("");
	const [showSpinner, setShowSpinner] = useState(false);
	const [noTransactionMsg, setNoTransactionMsg] = useState("");

	const cookies = new Cookies();
	const navigate = useNavigate();

	useEffect(() => {
		if (!cookies.get("token")) {
			navigate("/login");
		} else if (cookies.get("role") == "admin") {
			navigate("/adminTransactions");
		}
	}, []);

	useEffect(() => {
		const config = {
			headers: { Authorization: `Bearer ${cookies.get("token")}` },
			// "Content-Type": "application/json"
		};
		AccountService.getAllAccounts(config).then((res) => {
			if (res.status && res.status === 401) {
				navigate("/login");
			} else {
				setAccounts(res.data);
			}
		});
	}, []);

	const handleSubmitAccount = () => {
		setShowSpinner(true);
		const config = {
			headers: { Authorization: `Bearer ${cookies.get("token")}` },
		};
		TransactionHistoryService.getTransactions(
			{
				accNo: selectedAcc,
			},
			config
		).then((res) => {
			console.log("transaction", res);
			if (res.status && res.status === 401) {
				navigate("/login");
			} else if (res === "Incorrect MPIN") {
				setError(res);
			} else {
				setError("");
				setTransactions(res);
				setShowSpinner(false);
				if (res.length === 0) setNoTransactionMsg("No transactions to show");
				console.log("selectedacc",selectedAcc," ",res);
				console.log(typeof(selectedAcc), typeof(res[0].senderAccNo));
			}
		});
	};

	return (
		<div className="table-container">
			<h2>
				<center>Transaction History</center>
			</h2>
			<Form>
				<Form.Group
					controlId="mpin"
					autocomplete="off"
					className="Form-grp modal-form-grp"
				>
					<Form.Label>Select Account</Form.Label>
					<DropdownButton
						title={selectedAcc}
						onSelect={(e) => {
							setSelectedAcc(e);
						}}
					>
						<Dropdown.Menu>
							{accounts.map((acc) => (
								<Dropdown.Item eventKey={acc.accountNumber}>
									{acc.accountNumber}
								</Dropdown.Item>
							))}
						</Dropdown.Menu>
					</DropdownButton>
				</Form.Group>

				<Form.Group
					controlId="mpin"
					autocomplete="off"
					className="Form-grp modal-form-grp"
				>
					<Form.Label>MPIN</Form.Label>
					<Form.Control
						type="text"
						name="mpin"
						placeholder="Enter MPIN"
						value={mpin}
						onChange={(e) => {
							setMpin(e.target.value);
						}}
						required
					/>
				</Form.Group>
			</Form>
			<div>{error}</div>
			<div className="submit-transactions-btn">
				<Button variant="primary" type="submit" onClick={handleSubmitAccount}>
					Submit
				</Button>
			</div>
			<br></br>
			{showSpinner ? (
				<div className="spinner-div">
					<Spinner animation="border" role="status">
						<span className="visually-hidden">Loading...</span>
					</Spinner>
				</div>
			) : transactions.length === 0 ? (
				<div className="no-transaction-msg">{noTransactionMsg}</div>
			) : (
				<table className="table table-bordered table-hover">
					<thead>
						<tr>
							<th>Txn ID</th>
							<th>Sender's Acc No.</th>
							<th>Receiver's Acc No.</th>
							<th>Amount</th>
							<th>Status</th>
							<th>Timestamp</th>
						</tr>
					</thead>
					<tbody>
						{transactions.map((transaction) => (
							<tr>
								<td>{transaction.transactionId}</td>
								<td>{transaction.senderAccNo}</td>
								<td>{transaction.receiverAccNo}</td>
								<td>{transaction.amount}</td>
								{transaction.senderAccNo===parseInt(selectedAcc)?<td style={{color:"red"}}>DEBIT</td>:
								<td style={{color:"green"}}>CREDIT</td>}
								{/* <td style={transaction.senderAccNo===selectedAcc? {color:"red"}:{color:"green"}}>{transaction.amount}</td> */}
								<td>{new Date(transaction.timeStamp).toLocaleString()}</td>
							</tr>
						))}
					</tbody>
				</table>
			)}
		</div>
	);
}

export default TransactionHistory;
