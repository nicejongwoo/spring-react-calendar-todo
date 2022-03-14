import { useEffect, useReducer } from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom"
import { toast } from "react-toastify";
import AuthReducer from "../auth/AuthReducer";
import { signout } from "../auth/AuthService";
import SigninPage from "../auth/SigninPage";
import SignupPage from "../auth/SignupPage";
import CalendarPage from "../calendar/CalendarPage"
import Counter from "../components/Counter";
import TopMenu from "../components/TopMenu";
import HomePage from "../home/HomePage";



function AppRouter () {

    const initialState = { authenticated: false, token: {} }
    const [ state, dispatch ] = useReducer(AuthReducer, initialState)
    const { authenticated }  = state
    const { token }  = state
    const localStorageAuth = JSON.parse(localStorage.getItem('token'))
    const navigate = useNavigate()

    const getToken = (value) => {
        console.log('value?? ', value)
        if(value) {
            dispatch({
                type: 'SET_TOKEN',
                token: value,
                authenticated: true
            })
            localStorage.setItem('token', JSON.stringify(value))
        }
    }

    const handleLogout = () => {
        dispatch({
            type: 'DELETE_TOKEN',
        })
        signout()
        localStorage.removeItem('token')
        toast.success('로그아웃 되었습니다.')
        // window.location.href = '/'
        navigate('/')
    }

    useEffect(() => {
        if(localStorageAuth) {
            if (Object.keys(localStorageAuth).length !== 0) {
                dispatch({
                    type: 'SET_TOKEN',
                    token: localStorageAuth,
                    authenticated: true
                });
            } else {
                dispatch({
                    type: 'DELETE_TOKEN',
                });
                localStorage.removeItem('token')
                navigate('/')
            }
        }
    }, [authenticated]);

    return (
        <div>
            <TopMenu state={state} handleLogout={handleLogout} />
            <div className="container">
                <Routes>
                    <Route path="/" exact element={<HomePage/>} />
                    {authenticated && <Route path="/calendar" exact element={<CalendarPage  accessToken={token.accessToken} />} />}

                    {!authenticated && <Route path="/signup" exact element={<SignupPage/>} />}
                    {!authenticated && <Route path="/signin" exact element={<SigninPage getToken={getToken} />} />}
                    {authenticated && <Route path="/counter" exact element={<Counter />} />}
                </Routes>
            </div>
        </div>
    )
}

export default AppRouter;
