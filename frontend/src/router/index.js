import {createRouter, createWebHistory} from 'vue-router';
import GenerationPage from '@/views/GenerationPage.vue';
import ConfigurationPage from '@/views/ConfigurationPage.vue';

const routes = [
    {path: '/', name: 'GenerationPage', component: GenerationPage},
    {path: '/config', name: 'Configuration', component: ConfigurationPage}
];

/**
 *Vue Router instance.
 * @returns {Router} The router instance.
 */
const router = createRouter({
    history: createWebHistory(), routes,
});

export default router;
