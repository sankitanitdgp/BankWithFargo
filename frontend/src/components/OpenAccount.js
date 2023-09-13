import React, { useState } from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import '../styles/OpenAccount.css';

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
          <Form onSubmit={handleSubmit} className='Form'>
          <Form.Group controlId="title" autocomplete="off" className='Form-grp'>
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                name="title"
                placeholder="Miss/Mr./Mrs."
                value={formData.title}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="firstName" autocomplete="off" className='Form-grp'>
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

            <Form.Group controlId="lastName" autocomplete="off" className='Form-grp'>
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

            <Form.Group controlId="dob" autocomplete="off" className='Form-grp'>
              <Form.Label>DOB</Form.Label>
              <Form.Control
                type="date"
                name="dob"
                placeholder=""
                value={formData.dob}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="fatherName" autocomplete="off" className='Form-grp'>
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

            <Form.Group controlId="phone" autocomplete="off" className='Form-grp'>
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

            <Form.Group controlId="pan" autocomplete="off" className='Form-grp'>
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

            <Form.Group controlId="presentAddress" autocomplete="off" className='Form-grp'>
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

            <Form.Group controlId="permanentAddress" autocomplete="off" className='Form-grp'>
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

            <Form.Group controlId="occupation" autocomplete="off" className='Form-grp'>
              <Form.Label>Occupation Type</Form.Label>
              <Form.Control
                type="text"
                name="occupation"
                placeholder="Enter your occupation details"
                value={formData.occupation}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group controlId="grossIncome" autocomplete="off" className='Form-grp'>
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
              <div className='my-btn'>
              <Button variant="primary" type="submit" block>
              Create Account
            </Button>
              </div>
            
            </div>
            </span>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default SignUp;
