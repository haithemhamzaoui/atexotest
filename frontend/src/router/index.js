import {createRouter, createWebHistory} from 'vue-router';
import GenerationPage from '@/views/GenerationPage.vue';

const routes = [{path: '/', name: 'GenerationPage', component: GenerationPage}];

/**
 *Vue Router instance.
 * @returns {Router} The router instance.
 */
const router = createRouter({
    history: createWebHistory(), routes,
});

export default router;
