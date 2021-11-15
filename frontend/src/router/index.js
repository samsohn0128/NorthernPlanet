import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from './routes';
import store from '@/store';
import 'vue-toastification/dist/index.css';
import { methods } from '../util/toast.js';
Vue.prototype.$toastInfo = methods.toastInfo;

Vue.use(VueRouter);
const router = new VueRouter({
  //8443포트에서 테스트할때는 history 모드 주석처리하세요.
  mode: 'history',
  routes,
});

router.beforeEach(function (to, from, next) {
  var authRequired = to.matched.some(routeInfo => {
    // console.log(routeInfo);
    return routeInfo.meta.authRequired;
  });
  if (!authRequired || (authRequired && store.getters['users/getToken'])) {
    // console.log('authRequired : ' + authRequired);
    // console.log('isLogin : ' + store.state.users.login.isLogin);
    next();
  } else {
    // this.$toastInfo('로그인이 필요합니다');
    router.push('/login');
    // console.log('authRequired : ' + authRequired);
    // console.log('isLogin : ' + store.state.users.login.isLogin);
  }
});

export default router;
