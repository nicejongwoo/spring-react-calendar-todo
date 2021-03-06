import { toast } from "react-toastify";
import { deleteTodoApi, doneTodoApi, undoneTodoApi } from "./TodoService"

function TodoList({ todosByDate, completeAction, accessToken }) {

    const todos = []

    const thenApi = (response) => {
        completeAction(true)
        toast.success(response.message)
    }

    const deleteTodo = (token, e) => {
        // console.log('deleteTodo:: ', token)
        deleteTodoApi(token, accessToken).then((response) => {
            thenApi(response)
        });
    }

    const updateDone = (token, e) => {
        const currentStatus = e.target.value

        if(currentStatus === 'false') {
            //done처리 기존: false -> 변경: true
            doneTodoApi(token, accessToken).then((response) => {
                thenApi(response)
            })
        }

        if(currentStatus === 'true') {
            //undone처리 기존: true -> 변경: false
            undoneTodoApi(token, accessToken).then((response) => {
                thenApi(response)
            })
        }
    }

    todosByDate.map((todo) => {
        return (
            todos.push(
                <li key={todo.todoToken}>
                    <div className="todo-item">
                        <span>
                            <input
                                type="checkbox"
                                className=""
                                value={todo.done}
                                checked={todo.done}
                                // onChange={(e, token) => updateDone(e, `${todo.todoToken}`)}
                                onChange={updateDone.bind(this, `${todo.todoToken}`)}
                            />
                        </span>
                        <div className={`${todo.done ? 'done-todo' : ''}`}>{todo.title}</div>
                    </div>
                    <div className="todo-delete" onClick={deleteTodo.bind(null, `${todo.todoToken}`)}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash" viewBox="0 0 16 16">
                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                            <path fillRule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                        </svg>
                    </div>
                </li>
            )
        )
    })

    return (
        <div className="list-container col">
            <ul>{todos}</ul>
        </div>

    )
}

export default TodoList;
