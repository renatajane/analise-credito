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
                'Authorization': `Bearer ${token}`,
                ...options.headers,
            },
            body: JSON.stringify(data),
            ...options,
        });

        if (!response.ok) {
            throw new Error('Failed to post data to the API');
        }

        // return await response.json();
        return response;
    },
    async Get(endpoint){        
         const token = localStorage.getItem('__token');   
        // if (!token) throw new Error('Token não encontrado');
        const response = await axios.get(`${BASE_URL_LOCAL}/${endpoint}`, 
            { headers: { 'Authorization': `Bearer ${token}`, } } 
        )           
                return response;
    }
};

export default ApiService;
