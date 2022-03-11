import { Link } from "react-router-dom";
import logo from '../assets/logo.png'

export default function TopMenu () {
    return (
        <div className="navbar navbar-expand-md navbar-dark bd-navbar bg-dark">
            <nav className="container-xxl flex-wrap flex-md-nowrap">
                <Link to="/" className="navbar-brand p-0 me-2">
                    <img src={logo} width="32" height="32" className="d-block my-1"/>
                </Link>

                <div className="collapse navbar-collapse">
                    <ul className="navbar-nav flex-row flex-wrap bd-navbar-nav pt-2 py-md-0">
                        <li className="nav-item col-6 col-md-auto">
                            <Link to="/calendar" className="nav-link">Calendar</Link>
                        </li>
                    </ul>

                    <ul className="navbar-nav flex-row flex-wrap ms-md-auto">
                        <li className="nav-item col-6 col-md-auto">
                            <Link to="/signup" className="nav-link">
                                Sign Up
                            </Link>
                        </li>
                        <li className="nav-item col-6 col-md-auto">
                            <Link to="/signin" className="nav-link">
                                Login
                            </Link>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    )
}

