import { useEffect, useState } from "react";

//values, errors, submitting, handleChange, handleSubmit

function useForm({ initialValues, onSubmit, validate }){
// function useForm({ initialValues, validate }){
    const [values, setValues] = useState(initialValues)
    const [errors, setErrors] = useState({})
    const [submitting, setSubmitting] = useState(false)

    const handleChange = (event) => {
        const { name, value } = event.target
        setValues({ ...values, [name]: value })
        setErrors({ ...errors, [name]: ''})
    }

    const onSubmitPromise = (value) => {
        return new Promise((resolve, reject) => {
            console.log('value??? ', value)
            if(value) {
                resolve('OK')
            }else {
                reject('FAIL')
            }
        })
    }

    const handleSubmit = (event) => {
        // const { name } = event.target
        event.preventDefault()
        setSubmitting(true)
        setErrors(validate(values))

        onSubmit(values, errors)
            .then(()=>{
                setSubmitting(false)
            })
    }

    useEffect(() => {
        if(Object.keys(errors).length !== 0) {
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
