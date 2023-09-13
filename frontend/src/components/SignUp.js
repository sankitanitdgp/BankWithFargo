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
  

  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z]+\.[A-Za-z]{2,}$/i;
  const passwordRegex= /^.*(?=.{6,})(?=.*[a-zA-Z])(?=.*\d)(?=.*[!#$%&? "]).*$/i;
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };
  const handleEmailChange = (e) => {
    const { name, value } = e.target;
    setFormData({email:value, password:formData.password});
    if(!formData.email.match(emailRegex)){
      setEmailError("Invalid email!");
    } else {
      setEmailError("");
    }
  };

  const handlePasswordChange=(e)=>{
    const { name, value } = e.target;
    setFormData({email:formData.email, password:value});
    // if(!formData.password.match(passwordRegex)){
    //   setPasswordError("Invalid Password!");
    // } else {
    //   setPasswordError("");
    // }
  
    if (formData.password.length < 6) {
      setPasswordError("Password must be at least 6 characters long");
    } else {
      setPasswordError("");
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
          <h2 className="text-center">Sign Up</h2>
          <Form onSubmit={handleSubmit}  className='Form'>
            <Form.Group controlId="email" autocomplete="off" className='Form-grp'>
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
            <div className='form-errors'>{emailError}</div>
            <Form.Group controlId="password" autocomplete="off" className='Form-grp'>
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
            <div className='form-errors'>{passwordError}</div>
                  <br></br>

            <Form.Group controlId="confirmPassword" autocomplete="off" className='Form-grp'>
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

            <br></br>
            <div className='my-btn'>
            <Button variant="primary" type="submit" block>
              Sign Up
            </Button>
            </div>
            
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default SignUp;
