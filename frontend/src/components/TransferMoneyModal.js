import { useEffect, useState } from "react";
import Modal from "react-bootstrap/Modal";
import { AccountService } from "../service/AccountService";
import { useNavigate } from "react-router";
import Cookies from "universal-cookie";
import {
	Dropdown,
	Button,
	DropdownButton,
	Form,
	Spinner,
} from "react-bootstrap";
import "../styles/DepositMoneyModal.css";

function WithdrawMoneyModal(props) {
	const [show, setShow] = useState(props.show);
	const [balance, setBalance] = useState("");
	const [accounts, setAccounts] = useState([]);
	const [selectedAcc, setSelectedAcc] = useState("");
	const [mpin, setMpin] = useState("");
	const [amount, setAmount] = useState("");
	const [receiverAccountNumber, setReceiverAccNo] = useState("");
	const [message, setMessage] = useState("");
	const [showBalance, setShowBalance] = useState(false);
	const navigate = useNavigate();
	const cookies = new Cookies();
	const [showSpinner, setShowSpinner] = useState(false);
	const [error, setError] = useState(false);

	const handleClose = () => props.setShow(false);

	useEffect(() => {
		const config = {
			headers: { Authorization: `Bearer ${cookies.get("token")}` },
			// "Content-Type": "application/json"
		};
		AccountService.getAllAccounts(config).then((res) => {
			if (res.status && res.status === 401) {
				navigate("/");
			} else {
				setAccounts(res.data);
			}
		});
	}, []);

	const handleSubmitAccount = () => {
		setShowSpinner(true);
		if(selectedAcc===""){
			setError(true);
			setMessage("Please select an account");
			setShowSpinner(false);
			return;
		} else if(receiverAccountNumber===""){
			setError(true);
			setMessage("Receiver account cannot be empty");
			setShowSpinner(false);
			return;
		} else if(amount===""){
			setError(true);
			setMessage("Amount cannot be empty");
			setShowSpinner(false);
			return;
		}
		const config = {
			headers: { Authorization: `Bearer ${cookies.get("token")}` },
		};
		AccountService.transferMoney(
			{
				senderAccNumber: selectedAcc,
				mpin: mpin,
				amount: amount,
				receiverAccNumber: receiverAccountNumber,
			},
			config
		).then((res) => {
			setShowSpinner(false);
			if (res.status && res.status === 401) {
				navigate("/");
			} else {
				if (res === "Money transferred successfully!") setError(false);
				else setError(true);
				setMessage(res);
			}
		});
	};

	return (
		<>
			<Modal show={show} onHide={handleClose}>
				<Modal.Header closeButton>
					{/* <Modal.Title>Modal heading</Modal.Title> */}
				</Modal.Header>

				<Form>
					<Form.Group
						controlId="mpin"
						autocomplete="off"
						className="Form-grp modal-form-grp"
					>
						<Form.Label>Select Account to withdraw from</Form.Label>
						<Dropdown
							title={selectedAcc}
							onSelect={(e) => {
								setSelectedAcc(e);
							}}
						>
							<Dropdown.Toggle variant="primary" id="dropdown-basic drop-btn">
								{selectedAcc}
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

					<Form.Group
						controlId="receiverAccNo"
						autocomplete="off"
						className="Form-grp modal-form-grp"
					>
						<Form.Label>Receiver's Account Number </Form.Label>
						<Form.Control
							type="number"
							name="receiverAccountNo"
							placeholder="Enter receiver's Account No."
							value={receiverAccountNumber}
							onChange={(e) => {
								setReceiverAccNo(e.target.value);
							}}
							required
						/>
					</Form.Group>

					<Form.Group
						controlId="mpin"
						autocomplete="off"
						className="Form-grp modal-form-grp"
					>
						<Form.Label>MPIN</Form.Label>
						<Form.Control
							type="password"
							name="mpin"
							placeholder="Enter MPIN"
							value={mpin}
							onChange={(e) => {
								setMpin(e.target.value);
							}}
							required
						/>
					</Form.Group>

					<Form.Group
						controlId="amount"
						autocomplete="off"
						className="Form-grp modal-form-grp"
					>
						<Form.Label>Amount</Form.Label>
						<Form.Control
							type="number"
							name="amount"
							placeholder="Enter Amount"
							value={amount}
							onChange={(e) => {
								setAmount(e.target.value);
							}}
							required
						/>
					</Form.Group>
				</Form>
				{error && <div className="deposit-money-msg error-msg">{message}</div>}
				{!error && (
					<div className="deposit-money-msg">
						{message !== "" && <i className="fas fa-check tick-icon"></i>}
						{message}
					</div>
				)}
				<Button
					className="check-balance-btn"
					variant="primary"
					type="submit"
					onClick={handleSubmitAccount}
				>
					{showSpinner && (
						<Spinner animation="border" className="btn-spinner" role="status">
							<span className="visually-hidden">Loading...</span>
						</Spinner>
					)}
					Submit
				</Button>

				{showBalance && <Modal.Body>Your balance is Rs.{balance}</Modal.Body>}
				<Modal.Footer>
					<Button variant="secondary" color="blue" onClick={handleClose}>
						Close
					</Button>
				</Modal.Footer>
			</Modal>
		</>
	);
}

export default WithdrawMoneyModal;