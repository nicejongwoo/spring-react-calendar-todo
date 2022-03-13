import { toast } from "react-toastify";
import useForm from "./useForm"
import { createTodo } from "./TodoService"
import validate from "./validate"
import 'react-toastify/dist/ReactToastify.css';
import { useEffect, useState } from "react";


function TodoForm({ clickedEvent, completeSave, accessToken }) {
    const [ clickedDate, setClickedDate ] = useState('')

    const { values, errors, submitting, handleChange, handleSubmit } = useForm({
        initialValues: { title: '', todoDate: '' },
        onSubmit: (values) => {
            createTodo(values, accessToken).then((response) => {
                completeSave(true)
                toast.success(response.message)
            });
        },
        validate,
        clickedDate,
    })

    useEffect(() => {
        setClickedDate(clickedEvent.formattedDay)
        // setSaved(submitting)
    }, [clickedEvent, submitting])


    return (

        <div className="form-container col">
            <div className="form-area">
                <form onSubmit={handleSubmit} noValidate>
                    <div className="form-floating mb-3">
                        <input
                            type="text"
                            name="todoDate"
                            value={values.todoDate}
                            onChange={handleChange}
                            className={`form-control form-control-sm ${errors.todoDate ? 'is-invalid' : ''}`}
                            placeholder="예시) 2022-01-01"
                            id="todoDate"
                        />
                        <label htmlFor="todoDate">TodoDate<span className="required-field">*</span> : </label>
                        {errors.todoDate && <span className="invalid-feedback text-start">{errors.todoDate}</span>}
                    </div>

                    <div className="form-floating mb-3">
                        <input
                            type="text"
                            name="title"
                            value={values.title}
                            onChange={handleChange}
                            className={`form-control form-control-sm ${errors.title ? 'is-invalid' : ''}`}
                            placeholder="예시) Learn Spring and React"
                            id="title"
                        />
                        <label htmlFor="title">title<span className="required-field">*</span> : </label>
                        {errors.title && <span className="invalid-feedback text-start">{errors.title}</span>}
                    </div>

                    <div className="text-end">
                        <button className="btn btn-sm btn-outline-primary save-button" disabled={submitting}>저장</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default TodoForm;
