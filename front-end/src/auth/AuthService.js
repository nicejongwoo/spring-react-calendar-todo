import Cookies from "universal-cookie";
import { API_BASE_URL, headers, request } from "../services/RequestService";

const AUTH_BASE_URL = API_BASE_URL + '/auth'
const REFRESH_TOKEN_NAME = 'refreshToken'

const cookies = new Cookies()

export function signup(data) {
    return request({
        headers: headers,
        url: AUTH_BASE_URL + '/signup',
        method: 'POST',
        body: JSON.stringify(data),
    })
}

export function signin(data) {
    return request({
        headers: headers,
        url: AUTH_BASE_URL + '/signin',
        method: 'POST',
        body: JSON.stringify(data),
    })
}

export function signout() {
    window.localStorage.setItem('logout', Date.now())
    cookies.remove(REFRESH_TOKEN_NAME)
}

