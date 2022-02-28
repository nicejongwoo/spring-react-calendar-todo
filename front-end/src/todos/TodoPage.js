import React, { useEffect, useState } from "react";
import Calendar from "../calendar/calendar";
import TodoForm from './TodoForm'
// import TodoList from "./TodoList";

function TodoPage() {

    const [ loading, setLoading ] = useState(false)

    console.log('TodoPage loading:: ', loading)

    return (
        <div>
            <TodoForm setLoading={setLoading}/>
            {/* <TodoList /> */}
            <Calendar loading={loading}/>
        </div>
    )
}

export default TodoPage;