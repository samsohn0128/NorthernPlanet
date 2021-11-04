import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import Alertify from 'vue-alertify';
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';

Vue.config.productionTip = false;
Vue.use(Alertify);
Vue.use(Toast);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
