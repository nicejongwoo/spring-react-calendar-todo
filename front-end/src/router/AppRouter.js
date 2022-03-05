import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import CalendarPage from "../calendar/CalendarPage"

function AppRouter () {
    return (
        <div>
            <Router>
                <div className="container">
                    <Routes>
                        <Route path="/calendar" exact element={<CalendarPage/>} />
                    </Routes>
                </div>
            </Router>
        </div>
    )
}

export default AppRouter;
