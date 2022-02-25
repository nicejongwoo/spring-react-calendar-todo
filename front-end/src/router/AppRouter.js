import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import TodoPage from "../todos/TodoPage"

function AppRouter () {
    return (
        <div>
            <Router>
                <div className="container">
                    <Routes>
                        <Route path="/todos" exact element={<TodoPage/>} />
                    </Routes>
                </div>
            </Router>
        </div>
    )
}

export default AppRouter;