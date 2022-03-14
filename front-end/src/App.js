import { BrowserRouter } from 'react-router-dom';
import './App.css';
import Alert from './components/Alert';
import AppRouter from './router/AppRouter';

function App() {

    return (
        <div className='App'>
            <BrowserRouter>
                <AppRouter />
                <Alert />
            </BrowserRouter>
        </div>
    );
}

export default App;
