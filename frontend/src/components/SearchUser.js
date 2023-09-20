import React, { Component, useEffect, useState } from "react";
import "../styles/SearchUser.css";
import { UserService } from "../service/UserService";
import { useNavigate } from "react-router";
import Cookies from "universal-cookie"

function SearchUser() {
  const [searchAccount, setSearchAccount] = useState("");
  const [userData, setUserData] = useState([]);
  const navigate=useNavigate();
  const cookies=new Cookies();
  const [accounts, setAccounts]=useState([]);

  // Function to handle account number input change
  const handleAccountNumberChange = (event) => {
    setSearchAccount(event.target.value);
  };

  // Function to search for a user by account number
  const searchUser = () => {
    
  };

  // Function to toggle account status
  const toggleAccountStatus = (accountNumber) => {
    setUserData((prevData) =>
      prevData.map((user) => {
        if (user.accountNumber === accountNumber) {
          return { ...user, isEnabled: !user.isEnabled };
        }
        return user;
      })
    );
  };

  useEffect(() => {
    const config = {
      headers: { Authorization: `Bearer ${cookies.get("token")}` },
    };
    UserService.getAllUsers(config).then((res) => {
      //console.log(res);
      if (res.status && res.status === 401) {
        navigate("/login");
      } else {
        setAccounts(res);
      }
    });
  });

  return (
    <div>
      <h1>
        <center>Search User Details</center>
      </h1>
      <br></br>
      <center>
        <div className="search-container">
          <input
            type="text"
            placeholder="Enter Account Number"
            value={searchAccount}
            onChange={handleAccountNumberChange}
          />
          <button onClick={searchUser}>Search</button>
        </div>
      </center>
      <br></br>

      <table className="table">
        <thead>
          <tr>
            <th>Account Number</th>
            <th>Name</th>
            <th>Account Enabled</th>
          </tr>
        </thead>
        <tbody>
          {accounts.map((user) => (
            <tr key={user.accountNumber}>
              <td>{user.accountNumber}</td>
              <td>{`${user.title} ${user.firstName} ${user.lastName}`}</td>
              <td>
                <label className="switch">
                  <input
                    type="checkbox"
                    checked={user.isActive}
                    onChange={() => toggleAccountStatus(user.accountNumber)}
                  />
                  <span className="slider round"></span>
                </label>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
export default SearchUser;
