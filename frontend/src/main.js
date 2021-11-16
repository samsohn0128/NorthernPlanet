import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import 'vue-toastification/dist/index.css';
import { methods } from './util/toast.js';
import { validateMethods } from './util/validate.js';
Vue.config.productionTip = false;
Vue.prototype.$toastError = methods.toastError;
Vue.prototype.$toastSuccess = methods.toastSuccess;
Vue.prototype.$toastInfo = methods.toastInfo;
Vue.prototype.$toastWarning = methods.toastWarning;
Vue.prototype.$nameValidate = validateMethods.nameValidate;
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
