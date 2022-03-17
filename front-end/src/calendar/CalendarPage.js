import TodoPage from "./todo/TodoPage";
import Calendar from "./calendar";
import { useEffect, useState} from 'react'

function CalendarPage({accessToken}) {

    const [ clickedEvent, setClickedEvent ] = useState({})
    const [ isOpenPopup, setIsOpenPopup ] = useState(false)
    const [ isSaved, setIsSaved ] = useState(false)

    const getClickedInfo = (...values) => {
        setClickedEvent(JSON.parse(values))
    }

    useEffect(() => {
        if(clickedEvent.formattedDay) setIsOpenPopup(true)
        if(isSaved) setIsSaved(false)
    }, [clickedEvent, isSaved])

    return (
        <div className="calendar-page">
            <Calendar getClickedInfo={getClickedInfo} isSaved={isSaved} accessToken={accessToken} />
            {clickedEvent.formattedDay && isOpenPopup &&
                <TodoPage
                    clickedEvent={clickedEvent}
                    setIsOpenPopup={setIsOpenPopup}
                    setIsSaved={setIsSaved}
                    accessToken={accessToken}
                />}
        </div>
    )
}

export default CalendarPage;
