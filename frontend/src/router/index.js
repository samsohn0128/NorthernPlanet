import Vue from 'vue';
import VueRouter from 'vue-router';
import VueAlertify from 'vue-alertify';
import routes from './routes';
//import store from '@/store/index.js';

Vue.use(VueRouter);
Vue.use(VueAlertify);
const router = new VueRouter({
  routes,
  //mode: 'history',
});

// let isLogin =
// router.beforeEach(function (to, from, next) {
//   var authRequired = to.matched.some(routeInfo => {
//     // console.log(routeInfo);
//     return routeInfo.meta.authRequired;
//   });
//   if (!authRequired || (authRequired && store.state.users.login.isLogin)) {
//     // console.log('authRequired : ' + authRequired);
//     // console.log('isLogin : ' + store.state.users.login.isLogin);
//     next();
//   } else {
//     // VueAlertify.error('로그인이 필요합니다');
//     // this.$alertify.error('로그인이 필요합니다');
//     alert('로그인이 필요합니다');
//     router.push('/login');
//     // console.log('authRequired : ' + authRequired);
//     // console.log('isLogin : ' + store.state.users.login.isLogin);
//   }
// });

export default router;
