import React, { useState } from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';

const SignUp = () => {
  const [formData, setFormData] = useState({
    accountNumber: '',
    email: '',
    password: '',
    confirmPassword: '',
    mpin:'',
    confirmMpin:''
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
          <h2 className="text-center">Sign Up</h2>
          <Form onSubmit={handleSubmit}>
            <Form.Group controlId="username">
              <Form.Label>Account Number</Form.Label>
              <Form.Control
                type="text"
                name="accountNumber"
                placeholder="Enter account number"
                value={formData.accountNumber}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="email">
              <Form.Label>Email address</Form.Label>
              <Form.Control
                type="email"
                name="email"
                placeholder="Enter email"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="password">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                name="password"
                placeholder="Enter password"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="confirmPassword">
              <Form.Label>Confirm Password</Form.Label>
              <Form.Control
                type="password"
                name="confirmPassword"
                placeholder="Confirm password"
                value={formData.confirmPassword}
                onChange={handleChange}
                required
              />
              {errors.confirmPassword && (
                <Form.Text className="text-danger">{errors.confirmPassword}</Form.Text>
              )}
            </Form.Group>

            <Form.Group controlId="mpin">
              <Form.Label>MPIN</Form.Label>
              <Form.Control
                type="number"
                name="mpin"
                placeholder="Enter MPIN"
                value={formData.mpin}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="confirmMpin">
              <Form.Label>Confirm MPIN</Form.Label>
              <Form.Control
                type="number"
                name="confirmMpin"
                placeholder="Confirm MPIN"
                value={formData.confirmMpin}
                onChange={handleChange}
                required
              />
            </Form.Group>
            <br></br>
            <Button variant="primary" type="submit" block>
              Sign Up
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default SignUp;
