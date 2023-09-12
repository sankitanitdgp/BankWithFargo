import React, { useState } from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';

const SignUp = () => {
  const [formData, setFormData] = useState({
    title:'',
    firstName:'',
    lastName:'',
    dob:'',
    fatherName:'',
    accountNumber: '',
    phones:'',
    pan:'',
    email: '',
    presentAddress:'',
    permanentAddress:'',
    occupation:'',
    grossIncome:''
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Implement your validation logic here
    // For simplicity, we're assuming a basic validation for the password match
    if (formData.password !== formData.confirmPassword) {
      setErrors({ confirmPassword: 'Passwords do not match' });
    } else {
      // Submit your data to the server or perform further actions here
      console.log(formData);
    }
  };

  return (
    <Container>
      <Row className="justify-content-center">
        <Col md={6}>
          <h2 className="text-center">Open a Savings Account</h2>
          <Form onSubmit={handleSubmit}>
          <Form.Group controlId="title">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                name="Title"
                placeholder="Miss/Mr./Mrs."
                value={formData.title}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="firstName">
              <Form.Label>First Name</Form.Label>
              <Form.Control
                type="text"
                name="firstName"
                placeholder="Enter your first name"
                value={formData.firstName}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="lastName">
              <Form.Label>Last Name</Form.Label>
              <Form.Control
                type="text"
                name="lastName"
                placeholder="Enter your last name"
                value={formData.lastName}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="dob">
              <Form.Label>DOB</Form.Label>
              <Form.Control
                type="date"
                name="dob"
                placeholder=""
                value={formData.email}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="fatherName">
              <Form.Label>Father's Name</Form.Label>
              <Form.Control
                type="text"
                name="fatherName"
                placeholder="Enter your father's name"
                value={formData.fatherName}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="accountNumber">
              <Form.Label>Account Number</Form.Label>
              <Form.Control
                type="number"
                name="accountNumber"
                placeholder="Enter your Account Number"
                value={formData.accountNumber}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="phone">
              <Form.Label>Mobile Number</Form.Label>
              <Form.Control
                type="number"
                name="phoneNumber"
                placeholder="Enter your Mobile no."
                value={formData.phoneNumber}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="pan">
              <Form.Label>PAN</Form.Label>
              <Form.Control
                type="number"
                name="pan"
                placeholder="Enter your PAN"
                value={formData.pan}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="presentAddress">
              <Form.Label>Present Address</Form.Label>
              <Form.Control
                type="text"
                name="presentAddress"
                placeholder="Enter your Present Address"
                value={formData.presentAddress}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="permanentAddress">
              <Form.Label>Permanent Address</Form.Label>
              <Form.Control
                type="text"
                name="permanentAddress"
                placeholder="Enter your Permanent Address"
                value={formData.presentAddress}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="occupation">
              <Form.Label>Occupation Details</Form.Label>
              <Form.Control
                type="text"
                name="occupation"
                placeholder="Enter your occupation details"
                value={formData.occupation}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="grossIncome">
              <Form.Label>Gross Annual Income</Form.Label>
              <Form.Control
                type="number"
                name="grossIncome"
                placeholder="Enter your gross annual income in INR"
                value={formData.grossIncome}
                onChange={handleChange}
                required
              />
            </Form.Group>
            <br></br>
            <span>
            <div style={{ display: "flex" }}>
            <Button variant="primary" type="submit" block>
              Create Account
            </Button>
            </div>
            </span>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default SignUp;
