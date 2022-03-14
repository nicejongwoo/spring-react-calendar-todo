export default function AuthReducer(state, action) {
    switch(action.type) {
        case 'SET_TOKEN':
            return { ...state, token: action.token, authenticated: action.authenticated };
        case 'DELETE_TOKEN':
            return { ...state, token: {}, authenticated: false };
        default:
            return state;
    }

}
