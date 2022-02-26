import { useEffect, useState } from "react";

//values, errors, submitting, handleChange, handleSubmit

function useForm({ initialValues, onSubmit, validate }){
    const [values, setValues] = useState(initialValues)
    const [errors, setErrors] = useState({})
    const [submitting, setSubmitting] = useState(false)

    const handleChange = (event) => {
        const { name, value } = event.target //<-이부분 문법 공부필요
        setValues({ ...values, [name]: value }) //<-이부분 문법 공부필요
    }

    const handleSubmit = async (event) => {
        console.log('handleSubmit 시작')
        setSubmitting(true)
        event.preventDefault()
        //setErrors(validate(values))
        await new Promise((r) => { 
            setTimeout(r, 500) 
        }) //<-이부분 문법 공부필요        
        setErrors(validate(values))
        console.log('handleSubmit 종료')
    }

    useEffect(() => { //<-이부분 문법 공부필요
        if(submitting) {
            console.log('Object.keys(errors):: ', Object.keys(errors))
            if(Object.keys(errors).length === 0) { // Object.key()<-이부분 문법 공부필요
                onSubmit(values)
            }
            setSubmitting(false)
        }
    }, [errors])

    return {
        values,
        errors,
        submitting,
        handleChange,
        handleSubmit,
    }
}

export default useForm;