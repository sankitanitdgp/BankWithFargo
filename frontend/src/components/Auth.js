import { UserService, UserRegisterService } from '../service/UserService';
import React, { useState } from "react"
import '../App.css';
import "bootstrap/dist/css/bootstrap.min.css"
export default function (props) {
  let [authMode, setAuthMode] = useState("signin")

  const changeAuthMode = () => {
    setAuthMode(authMode === "signin" ? "signup" : "signin")
  }
  const loginUser = () => {
    // console.log(data);
    UserService.login({
      email: email,
      password: password
    }).then((data) => {
      console.log(data);
      setResponseMessage(data);
    })
      .catch((error) => console.log(error));
  }

  const addUser = () => {
    // validateForm();
    UserRegisterService.register({
      email: email,
      password: password
    }).then((data) => {
      console.log(data);
      setResponseMessage(data);
    })
      .catch((error) => console.log(error));
  }

  const [AccountNumber, setAccountNumber] = useState('')
  const [email, setEmail] = useState('')
  const [password, SetPassword] = useState('')
  const [responseMessage, setResponseMessage] = useState();
  // function validateForm()
  // {
  //   if(AccountNumber.length==0)
  //   {
  //     alert('Invalid Account Number')
  //     return
  //   }
  //   submitForm();

  // }

  if (authMode === "signin") {
    return (
      <div className="Auth-form-container">
        <form className="Auth-form">
          <div className="Auth-form-content">
            <h3 className="Auth-form-title">Sign In</h3>
            <div className="text-center">
              Not registered yet?{" "}
              <span className="link-primary" onClick={changeAuthMode}>
                Sign Up
              </span>
            </div>
            {/* <div className="form-group mt-3">
              <label>Account Number</label>
              <input
                type="number"
                className="form-control mt-1"
                placeholder="Enter account number"
                onChange={(e)=>setAccountNumber(e.target.value)}
              />
            </div> */}
            <div className="form-group mt-3">
              <label>Email address</label>
              <input
                type="email"
                className="form-control mt-1"
                placeholder="Enter email"
              />
            </div>
            <div className="form-group mt-3">
              <label>Password</label>
              <input
                type="password"
                className="form-control mt-1"
                placeholder="Enter password"
              />
            </div>
            {/* <div className="form-group mt-3">
              <label>PAN</label>
              <input
                type="text"
                className="form-control mt-1"
                placeholder="Enter pan number"
              />
            </div>
            <div className="form-group mt-3">
              <label>Mobile</label>
              <input
                type="number"
                className="form-control mt-1"
                placeholder="Enter mobile number"
              />
            </div>
            <div className="form-group mt-3">
              <label>DOB</label>
              <input
                type="date"
                className="form-control mt-1"
                placeholder="Enter date"
              />
            </div> */}
            {/* <div className="form-group mt-3">
              <label> Confirm Password</label>
              <input
                type="password"
                className="form-control mt-1"
                placeholder="Enter the same password"
              />
            </div> */}
            <div className="d-grid gap-2 mt-3">
              <button type="submit" className="btn btn-primary" onClick={addUser}>
                Submit
              </button>
            </div>
            <p className="text-center mt-2">
              Forgot <a href="#">password?</a>
            </p>
          </div>
        </form>
      </div>
    )
  }

  return (
    <div className="Auth-form-container">
      <form className="Auth-form">
        <div className="Auth-form-content">
          <h3 className="Auth-form-title">Sign In</h3>
          <div className="text-center">
            Already registered?{" "}
            <span className="link-primary" onClick={changeAuthMode}>
              Sign In
            </span>
          </div>
          <div className="form-group mt-3">
            <label>Full Name</label>
            <input
              type="email"
              className="form-control mt-1"
              placeholder="e.g Jane Doe"
            />
          </div>
          <div className="form-group mt-3">
            <label>Email address</label>
            <input
              type="email"
              className="form-control mt-1"
              placeholder="Email Address"
            />
          </div>
          <div className="form-group mt-3">
            <label>Password</label>
            <input
              type="password"
              className="form-control mt-1"
              placeholder="Password"
            />
          </div>
          <div className="d-grid gap-2 mt-3">
            <button type="submit" className="btn btn-primary" onClick={loginUser}>
              Submit
            </button>
          </div>
          <p className="text-center mt-2">
            Forgot <a href="#">password?</a>
          </p>
        </div>
      </form>
    </div>
  )
}