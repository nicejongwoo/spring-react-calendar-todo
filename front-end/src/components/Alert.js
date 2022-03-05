import { ToastContainer } from "react-toastify";

export default function Alert() {
    return (
        <ToastContainer
            position="bottom-center"
            hideProgressBar={true}
            autoClose={false}
            newestOnTop={false}
            rtl={false}
            pauseOnFocusLoss
            draggable
            pauseOnHover
        />
    )
}
