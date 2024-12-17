import Vue from 'vue';
import Router from 'vue-router';
import GenerationPage from '@/views/GenerationPage.vue';
import ConfigurationPage from '@/views/ConfigurationPage.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        { path: '/', name: 'GenerationPage', component: GenerationPage },
        { path: '/config', name: 'ConfigurationPage', component: ConfigurationPage },
    ],
});
