import React, { useState } from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import '../styles/Login.css';
import { UserLoginService } from '../service/UserService';

const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  // const [errors, setErrors] = useState("");
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z]+\.[A-Za-z]{2,}$/i;
  const passwordRegex= /^.*(?=.{6,})(?=.*[a-zA-Z])(?=.*\d)(?=.*[!#$%&? "]).*$/i;

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
   
      // Submit your data to the server or perform further actions here
      console.log(formData);
      UserLoginService.login(formData).then(res=>console.log(res))
    
  };

  return (
    <Container>
      <Row className="justify-content-center">
        <Col md={6}>
          <h3 className="text-center">Login</h3>
            <div>
              <Form onSubmit={handleSubmit} className="Form">
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
                <Form.Group controlId="password" autocomplete="off"  className='Form-grp'>
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
                <div className='form-errors'>{passwordError}</div>
                  <br></br>
                <div className="my-btn">
                  <Button variant="primary" type="submit" block>
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
