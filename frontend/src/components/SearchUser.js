import React, { Component, useEffect, useState } from "react";
import "../styles/SearchUser.css";
import { UserService } from "../service/UserService";
import { useNavigate } from "react-router";
import Cookies from "universal-cookie";
import { AccountService } from "../service/AccountService";
import { Spinner } from "react-bootstrap";

function SearchUser() {
	const [searchAccount, setSearchAccount] = useState("");
	const [userData, setUserData] = useState([]);
	const navigate = useNavigate();
	const cookies = new Cookies();
	const [accounts, setAccounts] = useState([]);
	const [enable, setEnable] = useState();
	const [showEmptyValueError, setShowEmptyValueError] = useState(false);
	const [statusArray, setStatusArray]=useState([]);

	// Function to handle account number input change
	const handleAccountNumberChange = (event) => {
		setSearchAccount(event.target.value);
	};

	// Function to search for a user by account number
	const searchUser = () => {
		if (searchAccount !== "") {
			setAccounts([]);
			setShowEmptyValueError(false);
			const config = {
				headers: { Authorization: `Bearer ${cookies.get("token")}` },
			};
			AccountService.getUserByAccountNo(
				{
					accNo: searchAccount,
				},
				config
			).then((res) => {
				if (res.status && res.status === 401) {
					navigate("/login");
				} else {
					console.log("accounts:", res);
					setAccounts([res]);
				}
			});
		} else {
			setShowEmptyValueError(true);
		}
	};

	// Function to toggle account status
	const toggleAccountStatus = (accountNumber,i) => {
		// setAccounts((prevData) =>
		// 	prevData.map((user) => {
		// 		if (user.accountNumber === accountNumber) {
		// 			return { ...user, isEnabled: !user.isEnabled };
		// 		}
		// 		return user;
		// 	})
		//);
		console.log(i);
		const config = {
			headers: { Authorization: `Bearer ${cookies.get("token")}` },
		};
		AccountService.changeAccountStatus(
			{
				accNo: accountNumber,
			},
			config
		).then((res) => {
			if (res.status && res.status === 401) {
				navigate("/login");
			} else {
				window.location.reload();
				console.log("res",res);
				var arr=statusArray;
				arr[i]=res;
				setStatusArray(arr);
				console.log("arr=",arr);
			}
		});
	};

	const formatDate = (date) => {
		let dateObj = new Date(parseInt(date));
		const yyyy = dateObj.getFullYear();
		let mm = dateObj.getMonth() + 1;
		let dd = dateObj.getDate();
		if (dd < 10) dd = "0" + dd;
		if (mm < 10) mm = "0" + mm;
		const formattedDate = dd + "/" + mm + "/" + yyyy;
		return formattedDate;
	};

	useEffect(() => {
		if (!cookies.get("token")) {
			navigate("/login");
		} else if (cookies.get("role") == "user") {
			navigate("/login");
		}
		const config = {
			headers: { Authorization: `Bearer ${cookies.get("token")}` },
		};
		UserService.getAllUsers(config).then((res) => {
			console.log(res);
			if (res.status && res.status === 401) {
				navigate("/login");
			} else {
				setAccounts(res);
				console.log(res);
				setStatusArray(res.map((acc)=>{
					return acc.accountStatus;
				}))
				console.log(res.map((acc)=>{
					return acc.accountStatus;
				}));
			}
		});
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
					<button type="submit" onClick={searchUser}>
						Search
					</button>
				</div>
				{showEmptyValueError && <div>enter some value</div>}
			</center>
			<br></br>
			{accounts.length === 0 ? (
				<div className="spinner-div">
					<Spinner animation="border" role="status">
						<span className="visually-hidden">Loading...</span>
					</Spinner>
				</div>
			) : accounts == "" ? (
				<div className="no-account-msg">No accounts found!</div>
			) : (
				<table className="table">
					<thead>
						<tr>
							<th>Account Number</th>
							<th>Name</th>
							<th>Date of Opening</th>
							<th>Enable/Disable</th>
						</tr>
					</thead>
					<tbody>
						{accounts.map((user,i) => (
							<tr key={user.accountNumber}>
								<td>{user.accountNumber}</td>
								<td>{`${user.title} ${user.firstName} ${user.lastName}`}</td>
								<td>{formatDate(user.dateOfOpening)}</td>
								<td>
									<label className="switch">
										<input
											type="checkbox"
											checked={statusArray[i]}
											onChange={() => toggleAccountStatus(user.accountNumber,i)}
										/>
										<span className="slider round"></span>
									</label>
								</td>
							</tr>
						))}
					</tbody>
				</table>
			)}
		</div>
	);
}
export default SearchUser;
