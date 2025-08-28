import axios from 'axios';

const httpClient = axios.create({
    headers: {
        "Content-type": "application/json"
    },
    withCredentials: true
});

export default httpClient;