import { ToastContainer } from "react-toastify";

export default function Alert() {
    return (
        <ToastContainer
            position="bottom-left"
            hideProgressBar={false}
            autoClose={3000}
            newestOnTop={false}
            rtl={false}
            pauseOnFocusLoss
            draggable
            pauseOnHover
        />
    )
}
