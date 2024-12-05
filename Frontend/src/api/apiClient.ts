import axios from 'axios';


const username = 'phuc';
const password = '123456Admin@';
const token = btoa(`${username}:${password}`);

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
  timeout: 10000,
  headers: {
    'Authorization': `Basic ${token}`, // Add Basic Auth header
  },
});

// apiClient.interceptors.request.use(
//   (config) => {
//     // Add token or other headers here
//     const token = localStorage.getItem('authToken');
//     if (token) {
//       config.headers.Authorization = `Bearer ${token}`;
//     }
//     return config;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );

// apiClient.interceptors.response.use(
//   (response) => response,
//   (error) => {
//     // Handle errors (e.g., redirect to login on 401)
//     if (error.response?.status === 401) {
//       // Handle unauthorized access
//     }
//     return Promise.reject(error);
//   }
// );

export default apiClient;
