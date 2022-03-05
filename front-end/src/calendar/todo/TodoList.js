
function TodoList(props) {

    const todos = []

    props.todosByDate.map((todo) => {
        return (
            todos.push(
                <tr key={todo.todoToken}>
                    <td>{todo.title}</td>
                    <td>{todo.todoDate}</td>
                    <td>{!todo.done && '진행중'}</td>
                </tr>
            )
        )
    })

    return (
        <div className="list-container col">
            {/* <div>
                <button onClick={handleClick}>2022-01-31</button>
            </div> */}
            <table className="table">
                <thead>

                </thead>
                <tbody>
                    <tr>
                        <th>제목</th>
                        <th>날짜</th>
                        <th>완료여부</th>
                    </tr>
                    {todos}
                </tbody>
            </table>
        </div>

    )
}

export default TodoList;
