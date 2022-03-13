import { useReducer } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import AuthReducer from "../auth/AuthReducer";
import { signout } from "../auth/AuthService";
import SigninPage from "../auth/SigninPage";
import SignupPage from "../auth/SignupPage";
import CalendarPage from "../calendar/CalendarPage"
import TopMenu from "../components/TopMenu";
import HomePage from "../home/HomePage";

function AppRouter () {

    const initialState = { authenticated: false, token: {} }
    const [ state, dispatch ] = useReducer(AuthReducer, initialState)
    const { authenticated } = state

    const getToken = (token) => {
        if(token) {
            dispatch({
                type: 'SET_TOKEN',
                token: token,
                authenticated: true
            })
        }
    }

    const handleLogout = () => {
        dispatch({
            type: 'DELETE_TOKEN',
        })
        signout()
    }

    return (
        <div>
            <Router>
                <TopMenu authenticated={authenticated} handleLogout={handleLogout} />
                <div className="container">
                    <Routes>
                        <Route path="/" exact element={<HomePage/>} />
                        {authenticated && <Route path="/calendar" exact element={<CalendarPage/>} />}
                        {!authenticated && <Route path="/signup" exact element={<SignupPage/>} />}
                        {!authenticated && <Route path="/signin" exact element={<SigninPage getToken={getToken} />} />}
                    </Routes>
                </div>
            </Router>
        </div>
    )
}

export default AppRouter;
