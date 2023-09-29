import React, { useEffect, useState } from "react";
import { Form, Button, Container, Row, Col, Spinner } from "react-bootstrap";
import "../styles/OpenAccount.css";
import { AccountService } from "../service/AccountService";
import { Link, redirect, useNavigate } from "react-router-dom";
import Cookies from "universal-cookie";
import SuccessModal from "./SuccessModal";

const OpenAccount = (props) => {
	const [formData, setFormData] = useState({
		title: "",
		firstName: "",
		lastName: "",
		dob: "",
		fatherName: "",
		phone: "",
		pan: "",
		mpin: "",
		presentAddress: "",
		permanentAddress: "",
		occupationType: "",
		income: "",
		email: "",
	});

	const cookies = new Cookies();
	const navigate = useNavigate();
	const [showSpinner, setShowSpinner] = useState(false);
	const [showSuccessModal, setShowSuccessModal] = useState(false);

	useEffect(() => {
		if (!cookies.get("token")) {
			navigate("/");
		}
	});

	const [confirmMpin, setConfirmMpin] = useState("");
	const [panError, setPanError] = useState("");
	const [phoneError, setPhoneError] = useState("");
	const [mpinError, setMpinError] = useState("");
	const [success, setSuccess] = useState("hidden");
	const handleTitleChange = (e) => {
		console.log(e.target.value);
		const { name, value } = e.target;
		setFormData({ ...formData, title: value });
	};

	const handleFirstNameChange = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, firstName: value });
	};

	const handleLastNameChange = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, lastName: value });
	};

	const handleEmailChange = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, email: value });
	};

	const handleDateOfBirth = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, dob: value });
	};

	const handleFatherName = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, fatherName: value });
	};

	const handlePhone = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, phone: value });
	};

	const handlePAN = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, pan: value.toUpperCase() });
	};

	const handleMPIN = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, mpin: value });
	};

	const handleConfirmMPIN = (e) => {
		const { name, value } = e.target;
		setConfirmMpin(value);
	};

	const handlePresentAddress = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, presentAddress: value });
	};

	const handlePermanentAddress = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, permanentAddress: value });
	};

	const handleOccupationType = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, occupationType: value });
	};

	const handleIncome = (e) => {
		const { name, value } = e.target;
		setFormData({ ...formData, income: value });
	};

	const handleSubmit = (e) => {
		e.preventDefault();
		setShowSpinner(true);
		setPhoneError("");
		setPanError("");
		setMpinError("");
		// Implement your validation logic here
		// For simplicity, we're assuming a basic validation for the password match
		if (formData.mpin !== confirmMpin) {
			setShowSpinner(false);
			setMpinError("MPIN passwords do not match");
		} else {
			// Submit your data to the server or perform further actions here
			console.log(formData);
			setMpinError("");
			const config = {
				headers: { Authorization: `Bearer ${cookies.get("token")}` },
			};
			AccountService.openAccount(formData, config).then((res) => {
				console.log("res", res);
				setShowSpinner(false);
				if (res.status && res.status === 401) {
					navigate("/");
				} else if (res.data) {
					if (
						res.data === "Invalid PAN number\nInvalid phone number\n" ||
						res.data === "Invalid phone number\nInvalid PAN number\n"
					) {
						setPhoneError("Invalid phone number");
						setPanError("Invalid PAN number");
					} else if (res.data === "Invalid PAN number\n") {
						setPanError("Invalid PAN number");
						setPhoneError("");
					} else if (res.data === "Invalid phone number\n") {
						setPhoneError("Invalid phone number");
						setPanError("");
					}
				} else {
					setPanError("");
					setPhoneError("");
					console.log("Account opened successfully: ", res);
					setShowSuccessModal(true);
				}
			});
			setSuccess("visible");
		}
	};

	return (
		<Container className="container-class">
			<Row className="justify-content-center">
				<Col md={6}>
					<div
						className="back-div"
						onClick={() => {
							navigate("/dashboard");
						}}
					>
						<i class="fas fa-chevron-left"></i> Back to dashboard
					</div>
					<h2 className="text-center">Open a Savings Account</h2>
					<Form onSubmit={handleSubmit} className="Form">
						<Form.Group
							controlId="title"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>Title</Form.Label>
							<div className="dropdown-select">
								<select
									name="title"
									value={formData.title}
									onChange={handleTitleChange}
									className="form-control"
									required
								>
									<option value="">Select</option>
									<option value="Miss">Miss</option>
									<option value="Mr.">Mr.</option>
									<option value="Mrs.">Mrs.</option>
								</select>
								<div className="dropdown-arrow"></div>
							</div>
						</Form.Group>

						<Form.Group
							controlId="firstName"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>First Name</Form.Label>
							<Form.Control
								type="text"
								name="firstName"
								placeholder="Enter your first name"
								value={formData.firstName}
								onChange={handleFirstNameChange}
								required
							/>
						</Form.Group>

						<Form.Group
							controlId="lastName"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>Last Name</Form.Label>
							<Form.Control
								type="text"
								name="lastName"
								placeholder="Enter your last name"
								value={formData.lastName}
								onChange={handleLastNameChange}
								required
							/>
						</Form.Group>
						{cookies.get("role") === "admin" && (
							<Form.Group
								controlId="email"
								autocomplete="off"
								className="Form-grp"
							>
								<Form.Label>Email ID</Form.Label>
								<Form.Control
									type="email"
									name="email"
									placeholder="Enter your Email ID"
									value={formData.email}
									onChange={handleEmailChange}
									required
								/>
							</Form.Group>
						)}

						<Form.Group controlId="dob" autocomplete="off" className="Form-grp">
							<Form.Label>DOB</Form.Label>
							<Form.Control
								type="date"
								name="dob"
								placeholder=""
								value={formData.dob}
								onChange={handleDateOfBirth}
								required
							/>
						</Form.Group>

						<Form.Group
							controlId="fatherName"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>Father's Name</Form.Label>
							<Form.Control
								type="text"
								name="fatherName"
								placeholder="Enter your father's name"
								value={formData.fatherName}
								onChange={handleFatherName}
								required
							/>
						</Form.Group>

						<Form.Group
							controlId="phone"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>Mobile Number</Form.Label>
							<Form.Control
								type="text"
								name="phoneNumber"
								placeholder="Enter your Mobile no."
								value={formData.phoneNumber}
								onChange={handlePhone}
								required
							/>
							<div className="open-acc-error">{phoneError}</div>
						</Form.Group>

						<Form.Group controlId="pan" autocomplete="off" className="Form-grp">
							<Form.Label>PAN</Form.Label>
							<Form.Control
								type="text"
								name="pan"
								placeholder="Enter your PAN"
								value={formData.pan.toUpperCase()}
								onChange={handlePAN}
								required
							/>
							<div className="open-acc-error">{panError}</div>
						</Form.Group>

						<Form.Group
							controlId="mpin"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>MPIN</Form.Label>
							<Form.Control
								type="text"
								name="mpin"
								placeholder="Enter MPIN"
								value={formData.mpin}
								onChange={handleMPIN}
								required
							/>
						</Form.Group>

						<Form.Group
							controlId="confirmMpin"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>Confirm MPIN</Form.Label>
							<Form.Control
								type="text"
								name="confirmMpin"
								placeholder="Confirm MPIN"
								value={formData.confirmMpin}
								onChange={handleConfirmMPIN}
								required
							/>
							<div className="open-acc-error">{mpinError}</div>
						</Form.Group>

						<Form.Group
							controlId="presentAddress"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>Present Address</Form.Label>
							<Form.Control
								type="text"
								name="presentAddress"
								placeholder="Enter your Present Address"
								value={formData.presentAddress}
								onChange={handlePresentAddress}
								required
							/>
						</Form.Group>

						<Form.Group
							controlId="permanentAddress"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>Permanent Address</Form.Label>
							<Form.Control
								type="text"
								name="permanentAddress"
								placeholder="Enter your Permanent Address"
								value={formData.permanentAddress}
								onChange={handlePermanentAddress}
								required
							/>
						</Form.Group>

						<Form.Group
							controlId="occupationType"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>Occupation Type</Form.Label>
							<Form.Control
								type="text"
								name="Type"
								placeholder="Enter your occupation details"
								value={formData.Type}
								onChange={handleOccupationType}
								required
							/>
						</Form.Group>

						<Form.Group
							controlId="income"
							autocomplete="off"
							className="Form-grp"
						>
							<Form.Label>Gross Annual Income</Form.Label>
							<Form.Control
								type="text"
								name="income"
								placeholder="Enter your gross annual income in INR"
								value={formData.income}
								onChange={handleIncome}
								required
							/>
						</Form.Group>
						<br></br>
						<span>
							<div style={{ display: "flex" }}>
								<div className="my-btn">
									<Button variant="primary" type="submit" block>
										{showSpinner && (
											<Spinner
												animation="border"
												className="btn-spinner"
												role="status"
											>
												<span className="visually-hidden">Loading...</span>
											</Spinner>
										)}
										Submit
									</Button>
								</div>
							</div>
						</span>
					</Form>
				</Col>
			</Row>
			{showSuccessModal && (
				<SuccessModal show={showSuccessModal} setShow={setShowSuccessModal} />
			)}
		</Container>
	);
};

export default OpenAccount;
