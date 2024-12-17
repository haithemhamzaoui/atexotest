import {createApp} from 'vue';
import App from './App.vue';
import router from './router';

const app = createApp(App);
// Registers the router instance in the application.
app.use(router);
app.mount('#app');
