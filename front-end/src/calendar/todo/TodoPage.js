import { useEffect, useState } from "react"
import { getTodoListByTodoDate } from "./TodoService"
import TodoForm from './TodoForm'
import TodoList from './TodoList'
import './Todo.css'

function TodoPage({ clickedEvent, setIsOpenPopup, setIsSaved }) {

    // const [ clickedDate, setClickedDate ] = useState('')
    const [ loading, setLoading ] = useState(false)
    const [ todosByDate, setTodosByDate ] = useState([])
    const [ saved, setSaved ] = useState(false)

    const closeForm = () => {
        setIsOpenPopup(false)
    }

    // const getTodosByDate = (value) => {
    //     setTodosByDate(value)
    // }

    useEffect(() => {
        getTodoListByTodoDate(clickedEvent.formattedDay).then((response) => {
            // console.log('response:: ', response)
            setTodosByDate(response.todos)
            setLoading(true)
        })

        if(saved) {
            setIsSaved(true)
        }

    }, [clickedEvent, saved])

    return (
        <div className="todo-popup">
            <div className="todo-container">
                <div className="todo-header">
                    <span>#</span>
                    <p className="todo-title">
                        {clickedEvent.formattedDay}
                    </p>
                    <button className="close-form" onClick={closeForm}>&times;</button>
                </div>
                <div className="todo-body row">
                    <TodoForm clickedEvent={clickedEvent} setSaved={setSaved} />
                    {(todosByDate.length > 0 || saved) && <TodoList todosByDate={todosByDate} />}
                </div>
            </div>
        </div>
    )
}

export default TodoPage;
