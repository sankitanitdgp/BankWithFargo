import { useEffect, useState } from "react";
import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import Cookies from "universal-cookie";
import "../styles/Nav.css";

function NavMe() {
	const cookies = new Cookies();
	const [token, setToken] = useState(cookies.get("token"));

	useEffect(() => {
		setToken(cookies.get("token"));
	}, [token]);

	return (
		<Navbar expand="lg" className="bg-body-tertiary">
			<Container>
				<Link style={{ textDecoration: "none" }} to="/">
					<Navbar.Brand className="">
						<span className="brand-font"> B</span>ank{" "}
						<span className="brand-font"> W</span>ith{" "}
						<span className="brand-font"> F</span>argo
					</Navbar.Brand>
				</Link>
				<Navbar.Toggle aria-controls="basic-navbar-nav" />
				<Navbar.Collapse id="basic-navbar-nav">
					<Nav className="me-auto align-right-nav-item">
						{/* {!token && <Nav.Link className="nav-item" href="/home">Home</Nav.Link>} */}
						{!token && (
							<Nav.Link className="nav-item" href="/">
								Login
							</Nav.Link>
						)}
						{!token && (
							<Nav.Link className="nav-item" href="/signup">
								Signup
							</Nav.Link>
						)}
						{token && (
							<Nav.Link className="nav-item" href="/dashboard">
								Dashboard
							</Nav.Link>
						)}
						{token && (
							<Nav.Link className="nav-item" href="/logout">
								Logout
							</Nav.Link>
						)}
						{/* <NavDropdown title="Dropdown" id="basic-nav-dropdown">
              <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
              <NavDropdown.Item href="#action/3.2">
                Another action
              </NavDropdown.Item>
              <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#action/3.4">
                Separated link
              </NavDropdown.Item>
            </NavDropdown> */}
					</Nav>
				</Navbar.Collapse>
			</Container>
		</Navbar>
	);
}

export default NavMe;
