import {createRouter, createWebHistory} from 'vue-router';
import GenerationPage from '@/views/GenerationPage.vue';

Vue.use(Router);

/**
 *Vue Router instance.
 * @returns {Router} The router instance.
 */
const router = createRouter({
    history: createWebHistory(), routes,
});

export default router;
