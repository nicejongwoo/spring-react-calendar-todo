import { toast } from "react-toastify";
import { API_BASE_URL, headers, request } from "../services/RequestService";

const AUTH_BASE_URL = API_BASE_URL + '/auth'

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
    toast.success('로그아웃 되었습니다.')
    // window.localStorage.setItem('logout', Date.now())
}
