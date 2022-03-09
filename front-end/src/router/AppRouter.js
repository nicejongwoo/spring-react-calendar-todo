import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom"
import Signin from "../auth/Signin";
import Signup from "../auth/Signup";
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
                        <Route path="/signup" exact element={<Signup/>} />
                        <Route path="/signin" exact element={<Signin/>} />
                    </Routes>
                </div>
            </Router>
        </div>
    )
}

export default AppRouter;
