import React, { useState } from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';

const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  const [errors, setErrors] = useState("");
  const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z]+\.[A-Za-z]{2,}$/i;
  const passwordRegex= /^[A-Za-z0-9]{8,}$/i;

  const handleEmailChange = (e) => {
    const { name, value } = e.target;
    setFormData({email:value, password:formData.password});
    if(!formData.email.match(emailRegex)){
      setErrors("Email not valid");
    } else {
      setErrors("");
    }
  };
 const handlePasswordChange=(e)=>{
  const { name, value } = e.target;
  setFormData({email:formData.email, password:value});
  if(!formData.password.match(passwordRegex)){
    setErrors("Password not valid");
  } else {
    setErrors("");
  }
 }
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
          <h2 className="text-center">Login</h2>
          <Form onSubmit={handleSubmit}>
    
            <Form.Group controlId="email">
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
            <div>{errors}</div>

            <Form.Group controlId="password">
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
            <br></br>
            <Button variant="primary" type="submit" block>
              Submit
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default Login;
