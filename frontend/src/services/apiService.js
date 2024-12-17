import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8082/api', headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    // Appel pour générer un numéro selon les données d'un inscrit
    generateNumber(data) {
        return apiClient.post('/generate', data);
    },

    // Appel pour configurer les critères de numérotation
    configureCriteria(config) {
        return apiClient.post('/config', config);
    },
};
