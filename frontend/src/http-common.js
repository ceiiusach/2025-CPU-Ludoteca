import axios from 'axios';

const backendServer = import.meta.env.VITE_BACKEND_SERVER;
const backendPort = import.meta.env.VITE_BACKEND_PORT;

const httpClient = axios.create({
    baseURL: `http://${backendServer}:${backendPort}`,
    headers: {
        "Content-type": "application/json"
    },
    withCredentials: true
});

export default httpClient;