import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from './routes';
import store from '@/store';

Vue.use(VueRouter);
const router = new VueRouter({
  //8443포트에서 테스트할때는 history 모드 주석처리하세요.
  // mode: 'history',
  routes,
});

router.beforeEach(function (to, from, next) {
  var authRequired = to.matched.some(routeInfo => {
    return routeInfo.meta.authRequired;
  });
  if (!authRequired || (authRequired && store.getters['users/getToken'])) {
    next();
  } else {
    router.push('/login');
  }
});

export default router;
