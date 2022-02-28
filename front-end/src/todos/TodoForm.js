import { toast } from "react-toastify";
import useForm from "../components/useForm"
import { createTodo } from "./TodoService"
import validate from "./validate"
import 'react-toastify/dist/ReactToastify.css';
import Alert from "../components/Alert";
import { useState } from "react";
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
    
    const { values, errors, submitting, handleChange, handleSubmit } = useForm({
        initialValues: { title: '', todoDate: '' },
        onSubmit: (values) => {
            createTodo(values).then((response) => {
                toast.success(response.message)
                props.setLoading(true)
            });
        },
        validate,
    })

    return (
        <form className="col" onSubmit={handleSubmit} noValidate>
            <div className="form-group">
                <label>Title</label>
                <input 
                    type="text"
                    name="title"
                    value={values.title} 
                    onChange={handleChange}
                    className={errors.title && "errorInput"}
                />
                {errors.title && <span className="errorMessage">{errors.title}</span>}
            </div>
            
            <div className="form-group">
                <label>todoDate</label>
                <input 
                    type="text"
                    name="todoDate"
                    value={values.todoDate} 
                    onChange={handleChange}
                    className={errors.todoDate && "errorInput"}
                />
                {errors.todoDate && <span className="errorMessage">{errors.todoDate}</span>}
            </div>

            <button className="btn btn-success" disabled={submitting}>저장</button>

            <Alert/>

            <ClipLoader color={color} loading={submitting} css={override} size={30} />
            {/* {submitting ? console.log('보내는 중') : console.log('완료')} */}
        </form>
    )
}

export default TodoForm;