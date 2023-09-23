import { useEffect, useState } from "react";
import Modal from "react-bootstrap/Modal";
import "../styles/SuccessModal.css";
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

function SuccessModal(props) {
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

	return (
		<>
			<Modal show={show} onHide={handleClose}>
				<Modal.Header closeButton>
					{/* <Modal.Title>Modal heading</Modal.Title> */}
				</Modal.Header>

				<Modal.Body className="success-msg">
					<i className="fas fa-check tick-icon"></i>
					Account opened successfully!
				</Modal.Body>

				<Modal.Footer>
					<Button
						variant="primary"
						color="blue"
						onClick={() => {
							navigate("/dashboard");
						}}
					>
						Go to dashboard
					</Button>
					<Button variant="secondary" color="blue" onClick={handleClose}>
						Close
					</Button>
				</Modal.Footer>
			</Modal>
		</>
	);
}

export default SuccessModal;
