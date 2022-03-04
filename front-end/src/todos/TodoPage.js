import React, { useEffect, useState } from "react";
import Calendar from "../calendar/calendar";
import TodoForm from './TodoForm'
import TodoList from "./TodoList";

function TodoPage() {

    const [ loading, setLoading ] = useState(false)
    const [ clickedDate, setClickedDate ] = useState('')
    const [ isChangeDay, setIsChangeDay ] = useState(false)
    const [ todosByDate, setTodosByDate ] = useState([]);

    const closeForm = () => {
        setClickedDate('')
    }

    return (
        <div>
            {clickedDate &&
            <div className="todo-container">
                <div className="todo-header">
                    <p className="todo-title">
                        {/* <span>TODO &lt; 오늘의 할 일을 적으세요. &gt;</span> */}
                    </p>
                    <button className="close-form" onClick={closeForm}>&times;</button>
                </div>
                {/* <div className="todo-body d-flex flex-nowrap"> */}
                <div className="todo-body row">
                    <TodoForm setLoading={setLoading} clickedDate={clickedDate} />
                    <TodoList todosByDate={todosByDate} />
                </div>

            </div>
            }
            <Calendar
                loading={loading}
                setClickedDate={setClickedDate}
                clickedDate={clickedDate}
                setTodosByDate={setTodosByDate}
            />
        </div>
    )
}

export default TodoPage;
