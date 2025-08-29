import axios from 'axios';

const httpClient = axios.create({
    headers: {
        "Content-type": "application/json"
    },
    withCredentials: true
});

httpClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('auth_token')
  if (token) {
    config.headers = config.headers ?? {}
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export default httpClient;