import { toast } from "react-toastify";
import useForm from "../components/useForm"
import { createTodo } from "./TodoService"
import validate from "./validate"
import 'react-toastify/dist/ReactToastify.css';
import Alert from "../components/Alert";
import { useEffect, useState } from "react";
import ClipLoader from "react-spinners/ClipLoader";
import { css } from "@emotion/react";

const override = css`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 auto;
  border-color: #36d7b7;
`;

function TodoForm(props) {
    const [ color, setColor ] = useState('#fff')
    const [ clickedDate, setClickedDate ] = useState('')

    const { values, errors, submitting, handleChange, handleSubmit } = useForm({
        initialValues: { title: '', todoDate: '' },
        onSubmit: (values) => {
            createTodo(values).then((response) => {
                toast.success(response.message)
                props.setLoading(true)
            });
        },
        validate,
        clickedDate,
    })

    useEffect(() => {
        setClickedDate(props.clickedDate)
    })

    return (

        <div className="form-container col">
            <div className="form-area">
                <form onSubmit={handleSubmit} noValidate>
                    <div className="row mb-3">
                        <label className="col-sm-2 col-form-label col-form-label-sm">DodoDate</label>
                        <div className="col-sm-10">
                            <input
                                type="text"
                                name="todoDate"
                                value={clickedDate}
                                onChange={handleChange}
                                className={`form-control form-control-sm ${errors.todoDate ? 'is-invalid' : ''}`}
                            />
                            {/* {errors.todoDate && <span className="invalid-feedback">{errors.todoDate}</span>} */}
                        </div>
                    </div>

                    <div className="row mb-3">
                        <label className="col-sm-2 col-form-label col-form-label-sm">Title</label>
                        <div className="col-sm-10">
                            <input
                                type="text"
                                name="title"
                                value={values.title}
                                onChange={handleChange}
                                className={`form-control form-control-sm ${errors.title ? 'is-invalid' : ''}`}
                            />
                            {/* {errors.title && <span className="invalid-feedback">{errors.title}</span>} */}
                        </div>
                    </div>

                    <button className="btn btn-success" disabled={submitting}>저장</button>

                </form>
                <Alert/>
                <ClipLoader color={color} loading={submitting} css={override} size={30} />
            </div>
        </div>
    )
}

export default TodoForm;
