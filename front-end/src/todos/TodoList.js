import { useState } from "react";
import { getTodoListByTodoDate } from "./TodoService";


function TodoList() {
    const [ todoDate, setTodoDate ] = useState('2022-01-31')

    const handleClick = (event) => {
        event.preventDefault()
        getTodoListByTodoDate(todoDate).then((response) => {
            console.log('response:: ', response)
        })
    }

    return (
        <div>
            <div>
                <button onClick={handleClick}>2022-01-31</button>
            </div>
            <table className="table">
                <thead>

                </thead>
                <tbody>
                    <tr>
                        <th>제목</th>
                        <th>날짜</th>
                        <th>완료여부</th>
                    </tr>
                </tbody>
            </table>
        </div>
        
    )
}

export default TodoList;