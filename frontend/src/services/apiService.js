import axios from 'axios';

const apiConfiguration = axios.create({
    baseURL: 'http://localhost:8081/api', headers: {
        'Content-Type': 'application/json',
    },
});

const apiGeneration = axios.create({
    baseURL: 'http://localhost:8082/api', headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    // Appel pour générer un id unique selon les données d'un inscrit
    generateId(data) {
        return apiGeneration.post('/generate', data);
    },
    // Appel pour chargé les generations s'ils existent
    getGenerations() {
        return apiGeneration.get('/generate');
    },
    // Appel pour chargé les configurations s'ils existent
    async getConfigurations() {
        return apiConfiguration.get('/config');
    },
    // Appel pour configurer les critères de numérotation
    configureCriteria(config) {
        return apiConfiguration.post('/config', config);
    },
    // Appel pour effacer les configurations sauvegardées
    resetConfigurations() {
        return apiConfiguration.delete('/config/reset');
    },
};
