import React, { useEffect, useState } from "react";
import Calendar from "../calendar/calendar";
import TodoForm from './TodoForm'
// import TodoList from "./TodoList";

function TodoPage() {

    const [ loading, setLoading ] = useState(false)
    const [ clickedDate, setClickedDate ] = useState('')

    // console.log('TodoPage loading:: ', loading)
    // console.log('TodoPage clickedDate:: ', clickedDate)

    return (
        <div>
            <TodoForm setLoading={setLoading} clickedDate={clickedDate} setClickedDate={setClickedDate}/>
            {/* <TodoList /> */}
            <Calendar loading={loading} setClickedDate={setClickedDate} clickedDate={clickedDate}/>
        </div>
    )
}

export default TodoPage;
