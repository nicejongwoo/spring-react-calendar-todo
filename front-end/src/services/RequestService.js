export const API_BASE_URL = "/api/v1"

export const headers = new Headers({
    'Content-Type': 'application/json',
})

export const request = (options) => {

    return fetch(options.url, options).then((response) =>
        response.json().then((json) => {
            if(!response.ok){
                return Promise.reject(json);
            }
            return json;
        })
    ).catch((error) => {
        return Promise.reject(error);
    })
}

const RequestService = () => {
    return {
        API_BASE_URL,
        headers,
        request
    }
}

export default RequestService;
