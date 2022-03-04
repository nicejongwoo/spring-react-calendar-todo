import { addDays, addMonths, endOfMonth, endOfWeek, format, getDay, isSameDay, isSameMonth, startOfMonth, startOfWeek, subMonths } from 'date-fns'
import { useEffect, useLayoutEffect, useState } from 'react'
import { getTodoListMonthly, getTodoListByTodoDate } from '../todos/TodoService'

import './calendar.css'

const Calendar = (props) => {

    const [ currentDate, setCurrrentDate ] = useState(new Date())
    const [ markToday, setMarkToday ] = useState(new Date())
    const [ todos, setTodos ] = useState([
        // { title: "test1", todoDate: "2022-02-02" },
        // { title: "test2", todoDate: "2022-02-11" },
        // { title: "test3", todoDate: "2022-02-11" },
    ])
    const [ loading, setLoading ] = useState(true)

    const monthStart = startOfMonth(currentDate)
    const monthEnd = endOfMonth(monthStart)

    const prevMonth = () => {
        setCurrrentDate(subMonths(currentDate, 1))
        setLoading(true)
        console.log('prevMonth')
    }

    const nextMonth = () => {
        setLoading(true)
        setCurrrentDate(addMonths(currentDate, 1))
        console.log('nextMonth')
    }

    const getTodoList = () => {
        const dateFormat = 'yyyy-MM-dd'
        const formattedStartDate = format(monthStart, dateFormat)
        const formattedEndDate = format(monthEnd, dateFormat)

        console.log('getTodoList')

        getTodoListMonthly(formattedStartDate, formattedEndDate).then((response) => {
            // console.log('response:: ', response)
            setTodos(response.todos)
        })
        setLoading(false)
    }



    const clickDay = (value) => {
        console.log('dateValue:: ', value)
        props.setClickedDate(value)

        getTodoListByTodoDate(value).then((response) => {
            console.log('response:: ', response)
            props.setTodosByDate(response.todos)
        })

    }

    useLayoutEffect(() => {
        console.log('useEffect')
        if(loading || props.loading){
            // const isLoading = true
            getTodoList(true)
        }
    }, [loading, props.loading])

    const header = () => {
        const dateFormat = 'yyyy-MM';

        return (
            <div className="header row flex-middle">
            <div className="col col-start">
                <div className="icon" onClick={prevMonth}>
                    chevron_left
                </div>
            </div>
            <div className="col col-content">
                <span>
                    {format(currentDate, dateFormat)}
                </span>
            </div>
            <div className="col col-end">
                <div className="icon" onClick={nextMonth}>
                    chevron_right
                </div>
            </div>
        </div>
        )
    }

    const daysOfWeek =() => {
        const dateFormat = 'eee'
        const days = []
        let startDate = startOfWeek(currentDate)

        for(let i=0; i<7; i++) {
            days.push(
                <div className='column col-center' key={i}>
                    {format(addDays(startDate, i), dateFormat)}
                </div>
            )
        }

        return <div className='days row'>{days}</div>

    }

    const cells = () => {
        const rows = []
        const startDate = startOfWeek(monthStart)
        const endDate = endOfWeek(monthEnd)
        const dayFormat = 'dd'
        const dateFormat = 'yyyy-MM-dd'

        let days = []
        let day = startDate
        let formattedDay = ''
        let formattedDate = ''

        // console.log('dmonthStartay:: ', monthStart) //Tue Feb 01 2022 00:00:00 GMT+0900 (한국 표준시)
        // console.log('monthEnd:: ', monthEnd) //Mon Feb 28 2022 23:59:59 GMT+0900 (한국 표준시)
        // console.log('startDate:: ', startDate) //Sun Jan 30 2022 00:00:00 GMT+0900 (한국 표준시)
        // console.log('endDate:: ', endDate) //Sat Mar 05 2022 23:59:59 GMT+0900 (한국 표준시)

        let todoByDate = {} //날짜를 key값으로 가지는 object
        let arrTodoTitle = [] //key값의 value를 배열로 등록(중복된 날짜의 value값 처리를 위해서)

        {todos.map((item, index) => {
            let todoDateDay = item.todoDate.slice(8, item.todoDate.length)
            arrTodoTitle.push(item.title)
            if(todoByDate.hasOwnProperty(todoDateDay)){
                todoByDate[todoDateDay].push(item.title) //해당 key가 존재하면 value 배열에 추가
            }else {
                todoByDate[todoDateDay] = arrTodoTitle
            }
            arrTodoTitle = []
        })}
        // console.log('todoByDate: ', todoByDate)

        let lis = []

        while(day <= endDate) {
            for(let i=0; i<7; i++){
                // const cloneDay = day
                formattedDay = format(day, dayFormat)
                formattedDate = format(day, dateFormat)


                {isSameMonth(day, monthStart) && Object.entries(todoByDate).map((item, index) => {
                    if(item[0] === formattedDay){
                        let liKey = ''
                        item[1].forEach((title, i) => {
                            liKey = formattedDay + '_' + i
                            // console.log('likey: ', liKey)
                            lis.push(<li key={liKey}><span>{title}</span></li>)
                        })
                    }
                })}

                days.push(
                    <div
                        className={
                            `column cell cell-${formattedDate} ${!isSameMonth(day, monthStart)
                            ? 'disabled'
                            : isSameDay(day, markToday)
                            ? 'selected'
                            // : isSameDay(day, clickedDate)
                            // ? 'clicked'
                            : ''
                            }`
                        }
                        key={formattedDate}
                        onClick={clickDay.bind(null, formattedDate)}
                    >
                        <div className='day-top'>
                            <span className='number'>{formattedDay}</span>
                            <span className='bg'>{formattedDay}</span>
                            <div></div>
                        </div>
                        <div className='day-body'>
                            <ul className='contents'>{formattedDay in todoByDate && lis}</ul>
                        </div>
                    </div>
                );
                day = addDays(day, 1)
                lis = []
            }
            rows.push(
                <div className='row' key={day}>{days}</div>
            );
            days = []
        }
        // console.log('lis: ', lis)


        return (
            <div className='body'>{rows}</div>
        )
    }

    return (
        <div className='calendar'>
            <div>{header()}</div>
            <div>{daysOfWeek()}</div>
            <div>{cells()}</div>
        </div>
    )
}

export default Calendar;
