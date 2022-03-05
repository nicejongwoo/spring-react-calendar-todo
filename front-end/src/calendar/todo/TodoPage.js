import { useEffect, useState } from "react"
import { getTodoListByTodoDate } from "./TodoService"
import TodoForm from './TodoForm'
import TodoList from './TodoList'
import './Todo.css'

function TodoPage({ clickedEvent, setIsOpenPopup, setIsSaved }) {

    const [ todosByDate, setTodosByDate ] = useState([])
    const [ saved, setSaved ] = useState(false)

    const closeForm = () => {
        setIsOpenPopup(false)
    }

    const completeSave = (result) => {
        console.log('===completeSave===', result)

        if(result) {
            setSaved(true)
            setIsSaved(true)
            // getTodoListByTodoDate()
        }
    }

    const completeDelete = (result) => {
        console.log('===completeDelete===', result)
        if(result) {
            setSaved(true)
            setIsSaved(true)
            // getTodoListByTodoDate()
        }
    }

    const getTodo = () => {
        console.log("getTodo")
        getTodoListByTodoDate(clickedEvent.formattedDay).then((response) => {
            console.log('getTodoListByTodoDate response:: ', response)
            setTodosByDate(response.todos)
            return false;
        })
    }

    useEffect(()  => {
        // console.log('clikedEffect')
        getTodo()
    }, [clickedEvent])

    useEffect(() => {
        // console.log('savedEffect')
        getTodo()
        setSaved(false)
    }, [saved])

    // if(clickedEvent) {
    //     getTodoListByTodoDate(clickedEvent.formattedDay).then((response) => {
    //         console.log('getTodoListByTodoDate response:: ', response)
    //         setTodosByDate(response.todos)
    //         return false;
    //     })
    // }

    // useEffect(() => {
    //     console.log('TodoPage useEffect clickedEvent?? :: ', clickedEvent)
    //     const getTodoListByTodoDate = () => {
    //         getTodoListByTodoDate(clickedEvent.formattedDay).then((response) => {
    //             console.log('getTodoListByTodoDate response:: ', response)
    //             setTodosByDate(response.todos)
    //         })
    //     }
    // }, [clickedEvent, completeSave, completeDelete])

    // useEffect(() => {
    //     if(saved) {
    //         setIsSaved(true)
    //     }
    //     getTodoListByTodoDate(clickedEvent.formattedDay).then((response) => {
    //         console.log('getTodoListByTodoDate response:: ', response)
    //         setTodosByDate(response.todos)
    //     })
    //     console.log('TodoPage useEffect saved? :: ', saved)
    // }, [saved])

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
                    <TodoForm clickedEvent={clickedEvent} completeSave={completeSave} />

                    {(todosByDate.length > 0 || saved) && <TodoList todosByDate={todosByDate} completeDelete={completeDelete}/>}
                </div>
            </div>
        </div>
    )
}

export default TodoPage;
