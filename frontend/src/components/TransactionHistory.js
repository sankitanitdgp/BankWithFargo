// import React, { useState } from 'react';
// import { Form, Button, Container, Row, Col } from 'react-bootstrap';

// const TransactionHistory = () => {
//     const [formData, setFormData] = useState({ 

//     });



    // TransactionHistory.js
import React, { Component } from 'react';

class TransactionHistory extends Component {
  state = {
    transactions: [], // Store transaction data here
  };

  // Fetch transaction data when the component mounts
  componentDidMount() {
    // Replace this with your actual API endpoint or data source
    fetch('https://api.example.com/transactions')
      .then((response) => response.json())
      .then((data) => {
        this.setState({ transactions: data });
      })
      .catch((error) => {
        console.error('Error fetching data: ', error);
      });
  }

  render() {
    const { transactions } = this.state;

    return (
      <div>
        <h2><center>Transaction History</center></h2>
        <br></br>
        <table className="table">
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
              <tr key={transaction.id}>
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
}

export default TransactionHistory;
