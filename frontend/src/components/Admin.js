import React, { useEffect, useState } from "react";
import { Row, Col } from "react-bootstrap";
import "../styles/Dashboard.css";
import { Link, useNavigate } from "react-router-dom";
import ViewBalanceModal from "./ViewBalanceModal";
import Cookies from "universal-cookie";
import DepositMoneyModal from "./DepositMoneyModal";
import SearchUser from "./SearchUser";

function AdminDashboard() {
	const [showSearchUser, setShowSearchUser] = useState(false);
	const cookies = new Cookies();
	const navigate = useNavigate();

	useEffect(() => {
		if (!cookies.get("token")) {
			navigate("/login");
		}
	});

	return (
		<>
			<div>
				<Row>
					<Col>
						<Link to="/openAccount" style={{ color: "black" }}>
							<div className="card-div">
								<div className="card-icon-div">
									<i class="fas fa-university"></i>
								</div>
								<div className="card-title">Open a new account</div>
							</div>
						</Link>
					</Col>
					<Col>
						<Link to="/searchUser" style={{ color: "black" }}>
							<div className="card-div">
								<div className="card-icon-div">
									<i class="fas fa-users"></i>
								</div>
								<div className="card-title">Search for User</div>
							</div>
						</Link>
					</Col>
					<Col>
						<Link to="/adminTransactions" style={{ color: "black" }}>
							<div className="card-div">
								<div className="card-icon-div">
									<i class="fas fa-wallet"></i>
								</div>
								<div className="card-title">View Transactions</div>
							</div>
						</Link>
					</Col>
				</Row>
			</div>
		</>
	);
}

export default AdminDashboard;
