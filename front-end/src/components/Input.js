
export default function Input (props) {

    let inputClassName = 'form-control form-control-sm'
    if(props.hasError !== undefined) {
        inputClassName += props.hasError ? ' is-invalid' : ' is-valid'
    }

    return (

        <div className="form-floating mb-3">
            <input
                type={props.type || 'text'}
                name={props.name}
                value={props.value}
                onChange={props.onChange}
                className={inputClassName}
                placeholder={props.placeholder}
                id={props.id}
                autoFocus={props.autofocus}
            />

            <label htmlFor={props.id}>{props.label}{props.isRequired && <span className='required-field'>*</span>} : </label>

            {props.hasError && <span className="invalid-feedback text-start">
                {props.error}
            </span>}
        </div>
    )
}
