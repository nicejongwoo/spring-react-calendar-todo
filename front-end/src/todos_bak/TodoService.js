// import axios from "axios";

const API_BASE_URL = "/api/v1/todos"

const headers = new Headers({
    'Content-Type': 'application/json',
})

const request = (options) => {

    return fetch(options.url, options).then((response) => 
        response.json().then((json) => {
            if(!response.ok){
                return Promise.reject(json);
            }
            return json;
        })
    ).catch((error) => {
        return Promise.reject(error);
    })
}

export function createTodo(todoData) {
    return request({
        headers: headers,
        url: API_BASE_URL,
        method: 'POST',
        body: JSON.stringify(todoData),
    })
    // .then((response) => {
    //     console.log('response::', response);
    //     //toast(response.message)
    // })
}

export function getTodoListByTodoDate(todoDate) {
    return request({
        headers: headers,
        url: API_BASE_URL + '/' + todoDate,
        method: 'GET',
    })
}

export function getTodoListMonthly(startDate, endDate) {
    return request({
        headers: headers,
        url: API_BASE_URL + '?startDate=' + startDate + '&endDate=' + endDate,
        method: 'GET',        
    })
}