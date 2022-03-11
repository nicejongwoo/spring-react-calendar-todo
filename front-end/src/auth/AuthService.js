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
