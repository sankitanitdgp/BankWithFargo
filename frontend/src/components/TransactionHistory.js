import React, { Component, useEffect, useState } from "react";
import "../styles/TransactionHistory.css";
import txn from "../utils/txn";
import Cookies from "universal-cookie";
import { useNavigate } from "react-router";

function TransactionHistory() {
  const [transactions, setTransactions] = useState(txn);
  const cookies=new Cookies();
  const navigate=useNavigate();

  useEffect(()=>{
    if(!cookies.get("token")){
      navigate("/login")
    }
  })

  return (
    <div className="table-container">
      <h2>
        <center>Transaction History</center>
      </h2>
      <br></br>
      <table className="table table-bordered table-hover">
        <thead>
          <tr>
            <th>Txn ID</th>
            <th>Sender's Acc No.</th>
            <th>Receiver's Acc No.</th>
            <th>Amount</th>
            <th>Timestamp</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map((transaction) => (
            <tr>
              <td>{transaction.id}</td>
              <td>{transaction.senderAccNo}</td>
              <td>{transaction.receiverAccNo}</td>
              <td>{transaction.amount}</td>
              <td>{transaction.timestamp}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TransactionHistory;
