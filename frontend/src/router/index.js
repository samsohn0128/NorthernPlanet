import Vue from 'vue';
import VueRouter from 'vue-router';
import routes from './routes';
//import store from '@/store/index.js';

Vue.use(VueRouter);
const router = new VueRouter({
  routes,
  mode: 'history',
});

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

router.beforeResolve(async (routeTo, routeFrom, next) => {
  // Create a `beforeResolve` hook, which fires whenever
  // `beforeRouteEnter` and `beforeRouteUpdate` would. This
  // allows us to ensure data is fetched even when params change,
  // but the resolved route does not. We put it in `meta` to
  // indicate that it's a hook we created, rather than part of
  // Vue Router (yet?).
  try {
    // For each matched route...
    for (const route of routeTo.matched) {
      await new Promise((resolve, reject) => {
        // If a `beforeResolve` hook is defined, call it with
        // the same arguments as the `beforeEnter` hook.
        if (route.meta && route.meta.beforeResolve) {
          route.meta.beforeResolve(routeTo, routeFrom, (...args) => {
            // If the user chose to redirect...
            if (args.length) {
              // If redirecting to the same route we're coming from...
              // Complete the redirect.
              next(...args);
              reject(new Error('Redirected'));
            } else {
              resolve();
            }
          });
        } else {
          // Otherwise, continue resolving the route.
          resolve();
        }
      });
    }
    // If a `beforeResolve` hook chose to redirect, just return.
  } catch (error) {
    return;
  }

  // If we reach this point, continue resolving the route.
  next();
});

export default router;
