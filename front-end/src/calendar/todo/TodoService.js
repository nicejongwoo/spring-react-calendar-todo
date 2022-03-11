import { API_BASE_URL, headers, request } from "../../services/RequestService"

const TODO_BASE_URL = API_BASE_URL + '/todos'


export function createTodo(todoData) {
    return request({
        headers: headers,
        url: TODO_BASE_URL,
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
        url: TODO_BASE_URL + '/' + todoDate,
        method: 'GET',
    })
}

export function getTodoListMonthly(startDate, endDate) {
    return request({
        headers: headers,
        url: TODO_BASE_URL + '/?startDate=' + startDate + '&endDate=' + endDate,
        method: 'GET',
    })
}

export function deleteTodoApi(token) {
    return request({
        headers: headers,
        url: TODO_BASE_URL + '/todo/' + token,
        method: 'DELETE',
    })
}

export function doneTodoApi(token) {
    return request({
        headers: headers,
        url: TODO_BASE_URL + '/todo/' + token + '/done',
        method: 'PUT',
    })
}

export function undoneTodoApi(token) {
    return request({
        headers: headers,
        url: TODO_BASE_URL + '/todo/' + token + '/undone',
        method: 'PUT',
    })
}
