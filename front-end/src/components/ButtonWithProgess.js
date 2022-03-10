
const ButtonWithProgress = (props) => {
    return (
        <button
            className="btn btn-primary"
            disabled={props.disabled}
        >
            {props.pnding && (
                <div className="spinner-border spinner-border-sm text-light mr-sm-1">
                    {/* <span className="sr-only">Loading...</span> */}
                </div>
            )}
            {props.text}
        </button>
    )
}

export default ButtonWithProgress;
