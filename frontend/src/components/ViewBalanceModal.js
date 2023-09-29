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
import "../styles/ViewBalanceModal.css";

function ViewBalanceModal(props) {
	const [show, setShow] = useState(props.show);
	const [balance, setBalance] = useState("");
	const [accounts, setAccounts] = useState([1, 2, 3]);
	const [selectedAcc, setSelectedAcc] = useState("");
	const [mpin, setMpin] = useState("");
	const [error, setError] = useState("");
	const [showBalance, setShowBalance] = useState(false);
	const navigate = useNavigate();
	const cookies = new Cookies();
	const [showSpinner, setShowSpinner] = useState(false);

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
		setShowBalance(false);
		const config = {
			headers: { Authorization: `Bearer ${cookies.get("token")}` },
		};
		AccountService.checkBalance(
			{
				accNo: selectedAcc,
				mpin: mpin,
			},
			config
		).then((res) => {
			setShowSpinner(false);
			console.log("balance", res);
			if (res.status && res.status === 401) {
				navigate("/");
			} else if (res === "Incorrect MPIN") {
				setError(res);
			} else {
				setError("");
				setBalance(res);
				setShowBalance(true);
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
						<Form.Label>Select Account</Form.Label>
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
				<div className="error-msg">{error}</div>
				{showBalance && (
					<Modal.Body className="balance">
						<i className="fas fa-check tick-icon"></i>
						Your balance is Rs.{balance}
					</Modal.Body>
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
				<Modal.Footer>
					<Button variant="secondary" color="blue" onClick={handleClose}>
						Close
					</Button>
				</Modal.Footer>
			</Modal>
		</>
	);
}

export default ViewBalanceModal;
