import { useReducer } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import { toast } from "react-toastify";
import AuthReducer from "../auth/AuthReducer";
import { signout } from "../auth/AuthService";
import SigninPage from "../auth/SigninPage";
import SignupPage from "../auth/SignupPage";
import CalendarPage from "../calendar/CalendarPage"
import TopMenu from "../components/TopMenu";
import HomePage from "../home/HomePage";

const initialState = { authenticated: false, token: {} }

function AppRouter () {

    const [ state, dispatch ] = useReducer(AuthReducer, initialState)
    const { authenticated } = state
    const { token } = state

    const getToken = (token) => {
        if(token) {
            dispatch({
                type: 'SET_TOKEN',
                token: token,
                authenticated: true
            })
        }
    }

    console.log('AppRouter::state:::', state)

    const handleLogout = () => {
        dispatch({
            type: 'DELETE_TOKEN',
        })
        signout()
        toast.success('로그아웃 되었습니다.')
    }

    return (
        <div>
            <Router>
                <TopMenu state={state} handleLogout={handleLogout} />
                <div className="container">
                    <Routes>
                        <Route path="/" exact element={<HomePage/>} />
                        {authenticated && <Route path="/calendar" exact element={<CalendarPage  accessToken={token.accessToken} />} />}
                        {!authenticated && <Route path="/signup" exact element={<SignupPage/>} />}
                        {!authenticated && <Route path="/signin" exact element={<SigninPage getToken={getToken} />} />}
                    </Routes>
                </div>
            </Router>
        </div>
    )
}

export default AppRouter;
