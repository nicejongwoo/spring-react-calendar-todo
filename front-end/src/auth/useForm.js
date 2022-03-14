import { useEffect, useState } from "react";

//values, errors, submitting, handleChange, handleSubmit

function useForm({ initialValues, validate }){
    const [values, setValues] = useState(initialValues)
    const [errors, setErrors] = useState({})
    const [submitting, setSubmitting] = useState(false)

    const handleChange = (event) => {
        const { name, value } = event.target
        setValues({ ...values, [name]: value })
        setErrors({ ...errors, [name]: ''})
    }

    const handleSubmit = (event) => {
        // const { name } = event.target
        // console.log('클릭')
        event.preventDefault()
        setSubmitting(true)
        setErrors(validate(values))
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
