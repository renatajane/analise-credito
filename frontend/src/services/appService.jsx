const BASE_URL_LOCAL = 'http://localhost:8080';
import axios from 'axios';

export const ApiService = {
    setToken(token) {
        authToken = token;
    },

    async Post(endpoint, data, options = {}) {
  
        const token = localStorage.getItem('__token');   
        // if (!token) throw new Error('Token não encontrado');
        const response = await fetch(`${BASE_URL_LOCAL}/${endpoint}`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
                ...options.headers,
            },
            body: JSON.stringify(data),
            ...options,
        });
        if (!response.ok) {
            throw new Error('Failed to post data to the API');
        }

        return response;
    },
    async Get(endpoint, options = {}){        
         const token = localStorage.getItem('__token');   
        // if (!token) throw new Error('Token não encontrado');
        const response = await axios.get(`${BASE_URL_LOCAL}/${endpoint}`, 
            { 
                headers: {
                     'Authorization': `Bearer ${token}`,
                     ...options.headers
                     }
             } 
        )           
        return response;
    }
};

export default ApiService;
