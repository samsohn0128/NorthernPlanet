import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import Alertify from 'vue-alertify';
import 'vue-toastification/dist/index.css';
import { methods } from './util/toast.js';
Vue.config.productionTip = false;
Vue.use(Alertify);
Vue.prototype.$toastError = methods.toastError;
Vue.prototype.$toastSuccess = methods.toastSuccess;
Vue.prototype.$toastInfo = methods.toastInfo;
Vue.prototype.$toastWarning = methods.toastWarning;
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
