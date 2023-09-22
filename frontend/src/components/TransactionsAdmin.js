import React, { useEffect, useState } from "react";
import {
	Button,
	Dropdown,
	DropdownButton,
	Form,
	Spinner,
} from "react-bootstrap";
import { useNavigate } from "react-router";
import Cookies from "universal-cookie";
import { TransactionHistoryService } from "../service/TransactionHistoryService";
import { UserService } from "../service/UserService";
import "../styles/TransactionHistory.css";

function TransactionHistory() {
	const [transactions, setTransactions] = useState([]);
	const [accounts, setAccounts] = useState([1, 2, 3]);
	const [selectedAcc, setSelectedAcc] = useState("");
	const [error, setError] = useState("");
	const [showSpinner, setShowSpinner] = useState(false);
	const [noTransactionMsg, setNoTransactionMsg] = useState("");

	const cookies = new Cookies();
	const navigate = useNavigate();

	useEffect(() => {
		if (!cookies.get("token")) {
			navigate("/login");
		} else if (cookies.get("role") === "user") {
			navigate("/login");
		}
	}, []);

	useEffect(() => {
		const config = {
			headers: { Authorization: `Bearer ${cookies.get("token")}` },
			// "Content-Type": "application/json"
		};
		UserService.getAllUsers(config).then((res) => {
			if (res.status && res.status === 401) {
				navigate("/login");
			} else {
				console.log(res);
				setAccounts(res);
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
			} else {
				setError("");
				setTransactions(res);
				setShowSpinner(false);
				if (res.length === 0) setNoTransactionMsg("No transactions to show");
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
					
	  				<Dropdown
						title={selectedAcc}
						onSelect={(e) => {
							setSelectedAcc(e);
						}}
					>
						<Dropdown.Toggle variant="success" id="dropdown-basic drop-btn">
        Select Account
      </Dropdown.Toggle>
						<Dropdown.Menu>
							{accounts.map((acc) => (
								<Dropdown.Item eventKey={acc.accountNumber}>
									{acc.accountNumber}
								</Dropdown.Item>
							))}
						</Dropdown.Menu>
					</Dropdown>
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
							<th>Timestamp</th>
						</tr>
					</thead>
					<tbody>
						{transactions.map((transaction) => (
							<tr>
								<td>{transaction.transactionId}</td>
								<td>{transaction.senderAccNo}</td>
								<td>{transaction.receiverAccNo}</td>
								<td style={transaction.senderAccNo===selectedAcc? {color:"red"}:{color:"green"}}>{transaction.amount}</td>
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
