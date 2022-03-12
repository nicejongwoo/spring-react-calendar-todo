import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom"
import SigninPage from "../auth/SigninPage";
import SignupPage from "../auth/SignupPage";
import CalendarPage from "../calendar/CalendarPage"
import TopMenu from "../components/TopMenu";
import HomePage from "../home/HomePage";

function AppRouter () {
    return (
        <div>
            <Router>
                <TopMenu />
                <div className="container">
                    <Routes>
                        <Route path="/" exact element={<HomePage/>} />
                        <Route path="/calendar" exact element={<CalendarPage/>} />
                        <Route path="/signup" exact element={<SignupPage/>} />
                        <Route path="/signin" exact element={<SigninPage/>} />
                    </Routes>
                </div>
            </Router>
        </div>
    )
}

export default AppRouter;
