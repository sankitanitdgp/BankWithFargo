import React, { useState } from "react";
import { Form, Button, Container, Row, Col } from "react-bootstrap";
import { UserRegisterService } from "../service/UserService";
import { Link, useNavigate } from "react-router-dom";

const SignUp = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    mpin: "",
    confirmMpin: "",
  });

  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z]+\.[A-Za-z]{2,}$/i;
  const [errors, setErrors] = useState({});
  const [showMessage, setShowMessage] = useState("hidden");
  const [message, setMessage]=useState("");
  const navigate=useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };
  const handleEmailChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      email: value,
      password: formData.password,
      confirmPassword: formData.confirmPassword,
    });
    if (!formData.email.match(emailRegex)) {
      setEmailError("Invalid email!");
    } else {
      setEmailError("");
    }
  };

  const handlePasswordChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      email: formData.email,
      password: value,
      confirmPassword: formData.confirmPassword,
    });

    if (value.length < 8) {
      setPasswordError("Password must be at least 8 characters long");
    } else {
      setPasswordError("");
    }
  };

  const handleConfirmPassword = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, confirmPassword: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Implement your validation logic here
    // For simplicity, we're assuming a basic validation for the password match
    if (formData.password !== formData.confirmPassword) {
      setErrors({ confirmPassword: "Passwords do not match" });
    } else {
      // Submit your data to the server or perform further actions here
      setErrors("");
      console.log(formData);
      UserRegisterService.register({
        email: formData.email,
        password: formData.password,
      }).then((res) => {
        if(res=="User already exists"){
            setMessage(res);
        } else {
          setMessage(res);
          navigate("/login");
        }
        setShowMessage(true);
      });
    }
  };

  return (
    <Container>
      <Row className="justify-content-center">
        <Col md={6}>
          <h2 className="text-center">Sign Up</h2>
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
              />
            </Form.Group>
            <div className="form-errors">{passwordError}</div>

            <Form.Group
              controlId="confirmPassword"
              autocomplete="off"
              className="Form-grp"
            >
              <Form.Label>Confirm Password</Form.Label>
              <Form.Control
                type="password"
                name="confirmPassword"
                placeholder="Confirm password"
                value={formData.confirmPassword}
                onChange={handleConfirmPassword}
                required
              />
              {errors.confirmPassword && (
                <Form.Text className="text-danger">
                  {errors.confirmPassword}
                </Form.Text>
              )}
            </Form.Group>

            <br></br>
            <div className="my-btn">
              <Button variant="primary" type="submit" block>
                Sign Up
              </Button>
            </div>
          </Form>
        </Col>
      </Row>
      <div style={{ visibility: showMessage }}>
        {message}
      </div>
    </Container>
  );
};

export default SignUp;
