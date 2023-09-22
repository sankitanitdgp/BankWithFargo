import React from "react";
import "./App.css";
import SignUp from "./components/SignUp";
import Home from "./components/Home";
import OpenAccount from "./components/OpenAccount";
import "bootstrap/dist/css/bootstrap.min.css";
import Login from "./components/Login";
import TransactionHistory from "./components/TransactionHistory";
import TransactionsAdmin from "./components/TransactionsAdmin";
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Admin from "./components/Admin";
import SearchUser from "./components/SearchUser";
import Logout from "./components/Logout";
import Main from "./components/Main";
import Nav from "./components/Nav";

function App() {
	return (
		<BrowserRouter>
		<Nav />
			<Routes>
				
				<Route path="/" element={<Login/>}/>
					
					{/* <Route path="login" element={<Login />} /> */}
					<Route path="/signup" element={<SignUp />} />
					<Route path="/openAccount" element={<OpenAccount />} />
					<Route path="/dashboard" element={<Dashboard />} />
					<Route path="/adminDashboard" element={<Admin />} />
					<Route path="/searchUser" element={<SearchUser />} />
					<Route path="/transactions" element={<TransactionHistory />} />
					<Route path="/adminTransactions" element={<TransactionsAdmin />} />
					<Route path="/logout" element={<Logout />} />
				
			</Routes>
		</BrowserRouter>
	);
}

export default App;
