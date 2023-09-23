import React, { useEffect, useState } from "react";
import { Form, Button, Container, Row, Col, Spinner } from "react-bootstrap";
import "../styles/Login.css";
import { UserService } from "../service/UserService";
import { Link, Outlet, redirect, useNavigate } from "react-router-dom";
import Cookies from "universal-cookie";
import Nav from "./Nav";

const Login = () => {
	const [formData, setFormData] = useState({
		email: "",
		password: "",
	});

	// const [errors, setErrors] = useState("");
	const [emailError, setEmailError] = useState("");
	const [passwordError, setPasswordError] = useState("");
	const [success, setSuccess] = useState("hidden");
	const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z]+\.[A-Za-z]{2,}$/i;
	const cookies = new Cookies();
	const navigate = useNavigate();
	const [showSpinner, setShowSpinner] = useState(false);

	useEffect(() => {
		if (cookies.get("token")) {
			navigate("/dashboard");
		}
	});

	const handleEmailChange = (e) => {
		const { name, value } = e.target;
		setFormData({ email: value, password: formData.password });
		if (!formData.email.match(emailRegex)) {
			setEmailError("Invalid email!");
		} else {
			setEmailError("");
		}
	};
	const handlePasswordChange = (e) => {
		const { name, value } = e.target;
		setFormData({ email: formData.email, password: value });
		// if(!formData.password.match(passwordRegex)){
		//   setPasswordError("Invalid Password!");
		// } else {
		//   setPasswordError("");
		// }

		if (value.length < 8) {
			setPasswordError("Password must be at least 8 characters long");
		} else {
			setPasswordError("");
		}
	};
	const handleSubmit = (e) => {
		e.preventDefault();
		setShowSpinner(true);
		UserService.login(formData).then((res) => {
			setShowSpinner(false);
			console.log(res);
			if (res === "Incorrect password") {
				setPasswordError(res);
			} else {
				cookies.set("token", res, { path: "/", maxAge: 10 * 1000 });
				if (formData.email === "admin6@gmail.com") {
					cookies.set("role", "admin");
					navigate("/adminDashboard");
				} else {
					cookies.set("role", "user");
					navigate("/dashboard");
				}
			}

			window.location.reload();
		});
		setSuccess("visible");
	};

	return (
		<Container className="container-class">
			<Row className="justify-content-center">
				<Col md={6}>
					<h3 className="text-center">Login</h3>
					<div>
						<Form onSubmit={handleSubmit} className="Form">
							<Form.Group
								controlId="email"
								autocomplete="off"
								className="Form-grp"
							>
								<Form.Label>Email address</Form.Label>
								<Form.Control
									autocomplete="off"
									type="email"
									name="email"
									placeholder="Enter email"
									value={formData.email}
									onChange={handleEmailChange}
									required
								/>
							</Form.Group>
							<div className="form-errors">{emailError}</div>
							<Form.Group
								controlId="password"
								autocomplete="off"
								className="Form-grp"
							>
								<Form.Label>Password</Form.Label>
								<Form.Control
									type="password"
									name="password"
									placeholder="Enter password"
									value={formData.password}
									onChange={handlePasswordChange}
									required
									autocomplete="off"
								/>
							</Form.Group>
							<div className="form-errors">{passwordError}</div>
							<br></br>
							<span>
								<div class="redirect">
									First-time user? <a href="/signup">Sign-up</a>
								</div>
								<div class="pwd-redirect">
									Forgot <a href="#">password?</a>
								</div>
							</span>
							<br></br>
							<br></br>
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
						</Form>
					</div>
				</Col>
			</Row>
		</Container>
	);
};

export default Login;
