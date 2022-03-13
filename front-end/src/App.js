import './App.css';
import Alert from './components/Alert';
import AppRouter from './router/AppRouter';

function App() {

    return (
        <div className='App'>

            <AppRouter />
            <Alert />
        </div>
    );
}

export default App;
