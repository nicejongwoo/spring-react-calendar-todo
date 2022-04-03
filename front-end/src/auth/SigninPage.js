import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import ButtonWithProgress from "../components/ButtonWithProgess";
import Input from "../components/Input";
import { signin } from "./AuthService";
import useForm from "./useForm";
import validate from "./validate";


function Signin ({ getToken }) {

    const initialValues = { email: '', password: '' }
    const navigate = useNavigate();

    const { values, errors, submitting, handleChange, handleSubmit } = useForm({
        initialValues: initialValues,
        validate,
    })

    useEffect(() => {
        if(submitting && Object.keys(errors).length === 0) {
            signin(values).then((response) => {
                const token = {'accessToken': response.accessToken, 'refreshToken': response.refreshToken }
                getToken(token)
                toast.success(response.message)
                navigate('/')
            }).catch((response) => {
                toast.error(response.message)
            })
        }
    }, [handleSubmit])

    return (
        <div className="container">

            <h1 className="text-center">로그인</h1>

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
                        autofocus={true}
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
                        disabled={false}
                        pnding={false}
                        text="로그인"
                    />
                </div>
            </form>
        </div>
    )
}

export default Signin;
