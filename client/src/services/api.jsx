import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:80',
});

export default api;