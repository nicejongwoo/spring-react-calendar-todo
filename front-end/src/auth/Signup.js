import { useEffect, useState } from "react";
import ButtonWithProgress from "../components/ButtonWithProgess";
import Input from "../components/Input";
import useForm from "./useForm";
import validate from "./validate";

function Signup () {

    const initialValues = { email: '', userName: '', password: '' }

    const { values, errors, submitting, handleChange, handleSubmit } = useForm({
        initialValues: initialValues,
        onSubmit: (values, errors) => {
            // console.log('onSubmit values: ', values, errors)
            // console.log('submitting:: ', submitting)
            // setPending(true)
            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    //TODO: sing up request
                    resolve('OK')
                    // reject('FAIL')
                }, 1000)
            }).then((r)=>{
                console.log('r: ', r)
            }).catch((e) => {
                console.log('e: ', e)
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

        </div>
    )
}

export default Signup;
