// import Vue from 'vue';
//import { searchUsers } from '@/api/users.js';
// import router from '@/router/index.js';

import router from '../../router';
export default {
  namespaced: true,
  state: () => ({
    test: 'test',
    users: [], // 전체 사용자
    user: {
      userId: '',
      email: '',
      name: '',
      image: '',
      oauthId: '',
      token: '',
    },
  }),
  mutations: {
    SET_LOGIN(state, payload) {
      state.login.isLogin = true;
      state.login.userid = payload.id;
      state.login.username = payload.name;
      state.login.useremail = payload.email;
      router.push('/dashboard');
    },
    SET_LOGOUT(state) {
      state.user.userId = '';
      state.user.email = '';
      state.user.name = '';
      state.user.image = '';
      state.user.oauthId = '';
      state.user.token = '';
      router.push('/');
    },
    SET_ALL_USERS(state, payload) {
      state.users = payload;
    },
    SET_NAME(state, payload) {
      state.user.name = payload;
    },
    SET_USER(state, payload) {
      state.user.userId = payload.userId;
      state.user.email = payload.email;
      state.user.name = payload.name;
      state.user.image = payload.image;
      state.user.oauthId = payload.oauthId;
      state.user.token = payload.token;
    },
  },
  actions: {
    // GET_ALL_USERS(context, payload) {
    //   searchUsers(payload)
    //     .then(({ data }) => {
    //       context.commit('SET_ALL_USERS', data);
    //     })
    //     .catch(err => {
    //       console.log('get all users actions err ', err);
    //     });
    // },
    SET_NAME({ commit }, username) {
      commit('SET_NAME', username);
    },
  },
  getters: {
    isLogin(state) {
      return state.login.isLogin;
    },
    getUserId(state) {
      return state.user.userId;
    },
    userInfo(state) {
      return state.login;
    },
    users(state) {
      return state.users;
    },
    getUser(state) {
      return state.user;
    },
    getToken(state) {
      return state.user.token;
    },
  },
};
