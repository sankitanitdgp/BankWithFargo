import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
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
				<Link className="card-link" to="/openAccount">
					<Card className="dashboard-card card-img-">
						<Card.Body>Create account</Card.Body>
					</Card>
				</Link>

				<Link className="card-link" to="/searchUser">
					<Card className="dashboard-card">
						<Card.Body>Search User</Card.Body>
					</Card>
				</Link>

				<Link className="card-link" to="/adminTransactions">
					<Card className="dashboard-card">
						<Card.Body>View User Transactions</Card.Body>
					</Card>
				</Link>
				{/* {showSearchUser && (
          <SearchUser show={showSearchUser} setShow={setShowSearchUser} />
        )} */}
			</div>
		</>
	);
}

export default AdminDashboard;
