import React, { useState } from "react";
import Calendar from "../calendar/calendar";
import TodoForm from './TodoForm'
import TodoList from "./TodoList";

function TodoPage() {

    const [ loading, setLoading ] = useState(false)
    console.log('loading:: ', loading)

    return (
        <div>
            <TodoForm setLoading={setLoading}/>
            {/* <TodoList /> */}
            <Calendar loading={loading} setLoading={setLoading}/>
        </div>
    )
}

export default TodoPage;