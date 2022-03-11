import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import Alert from "../components/Alert";
import ButtonWithProgress from "../components/ButtonWithProgess";
import Input from "../components/Input";
import { signup } from "./AuthService";
import useForm from "./useForm";
import validate from "./validate";

function Signup () {

    const initialValues = { email: '', userName: '', password: '' }
    const navigate = useNavigate();

    const { values, errors, submitting, handleChange, handleSubmit, setSubmitting } = useForm({
        initialValues: initialValues,
        onSubmit: (values) => {
            signup(values).then((response) => {
                console.log('response:: ', response)
                toast.success(response.message)
                navigate('/signin')
            }).catch((response) => {
                // console.log('response:: ', response)
                toast.error(response.message)
                setSubmitting(false)
            })
        },
        validate,
    })


    return (
        <div className="container">

            <h1 className="text-center">Sign Up</h1>

            <form onSubmit={handleSubmit} noValidate>
                <div className="col-12 mb-3">
                    <Input
                        label="이메일"
                        name="email"
                        id="email"
                        placeholder="gildong@email.com"
                        value={values.email}
                        onChange={handleChange}
                        hasError={errors.email && true}
                        error={errors.email}
                        isRequired={true}
                    />
                </div>
                <div className="col-12 mb-3">
                    <Input
                        label="이름"
                        name="userName"
                        id="userName"
                        placeholder="honggildong"
                        value={values.userName}
                        onChange={handleChange}
                        hasError={errors.userName && true}
                        error={errors.userName}
                        isRequired={true}
                    />
                </div>
                <div className="col-12 mb-3">
                    <Input
                        label="비밀번호"
                        type="password"
                        name="password"
                        id="password"
                        placeholder="12"
                        value={values.password}
                        onChange={handleChange}
                        hasError={errors.password && true}
                        error={errors.password}
                        isRequired={true}
                    />
                </div>
                <div className="text-center">
                    <ButtonWithProgress
                        className="btn btn-primary"
                        disabled={submitting}
                        pnding={submitting}
                        text="Sign Up"
                    />
                </div>
            </form>
            <Alert />
        </div>
    )
}

export default Signup;
