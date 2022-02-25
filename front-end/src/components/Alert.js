import { ToastContainer } from "react-toastify";

export default function Alert() {
    return (
        <ToastContainer 
            position="bottom-left"
            hideProgressBar={true}
            autoClose={3000}
            newestOnTop={false}
            rtl={false}
            pauseOnFocusLoss
            draggable
            pauseOnHover
        />
    )
}