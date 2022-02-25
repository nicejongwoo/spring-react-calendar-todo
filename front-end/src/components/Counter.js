import { useState } from "react";

function Counter() {
    const [counter, setCounter] = useState(0)
    const handleClick = () => {
        setCounter(counter + 1)
    }
    
    return (
        <div className="container">
            <button onClick={handleClick}>더하기</button>
            <span>{counter}</span>
        </div>
        
    )
}

export default Counter;