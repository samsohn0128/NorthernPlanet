import Vue from 'vue';
import Vuex from 'vuex';
import meetingRoom from './modules/meetingRoom.js';
import users from './modules/users.js';
import rooms from './modules/rooms.js';
import mypage from './modules/mypage.js';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  getters: {},
  modules: {
    meetingRoom,
    users,
    rooms,
    mypage,
  },
  plugins: [
    createPersistedState({
      paths: ['users', 'rooms', 'mypage'],
    }),
  ],
});

export default store;
