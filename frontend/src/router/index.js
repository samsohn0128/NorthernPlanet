import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from './routes';
import store from '@/store';
//import Toast from 'vue-toastification';
//Vue.use(Toast);
Vue.use(VueRouter);
const router = new VueRouter({
  //8443포트에서 테스트할때는 history 모드 주석처리하세요.
  mode: 'history',
  routes,
});

router.beforeEach(function (to, from, next) {
  var authRequired = to.matched.some(routeInfo => {
    return routeInfo.meta.authRequired;
  });
  if (!authRequired || (authRequired && store.getters['users/getToken'])) {
    next();
  } else {
    // Vue.$toast.warning('로그인이 필요합니다', {
    //   timeout: 2000,
    //   draggable: false,
    //   position: 'bottom-right',
    //   pauseOnFocusLoss: false,
    //   pauseOnHover: false,
    // });

    router.push('/login');
  }
});

export default router;
