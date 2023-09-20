import React, { Component, useEffect, useState } from "react";
import "../styles/SearchUser.css";

function SearchUser() {
  const [searchAccount, setSearchAccount] = useState("");
  const [userData, setUserData] = useState([]);

  // Simulated user data for demonstration purposes
  const mockUserData = [
    { accountNumber: "12345", name: "John Doe", isEnabled: true },
    { accountNumber: "67890", name: "Jane Smith", isEnabled: false },
  ];

  // Function to handle account number input change
  const handleAccountNumberChange = (event) => {
    setSearchAccount(event.target.value);
  };

  // Function to search for a user by account number
  const searchUser = () => {
    // Simulate searching by filtering the mock user data
    const user = mockUserData.find(
      (user) => user.accountNumber === searchAccount
    );
    if (user) {
      setUserData([user]);
    } else {
      setUserData([]);
    }
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
    // Initially, display all users
    setUserData(mockUserData);
  }, []);

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
          {userData.map((user) => (
            <tr key={user.accountNumber}>
              <td>{user.accountNumber}</td>
              <td>{user.name}</td>
              <td>
                <label className="switch">
                  <input
                    type="checkbox"
                    checked={user.isEnabled}
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
