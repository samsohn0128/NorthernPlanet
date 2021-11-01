export default {
  namespaced: true,
  state: () => ({
    currentPPTID: null,
    currentPPTTitle: null,
    size: null,
    transition: null,
  }),
  mutations: {
    GET_CURRENT_PRESENTATION_INFO: function (state, data) {
      state.currentPPTID = data.presentationId;
      state.currentPPTTitle = data.presentationName;
      console.log(state, 'get');
    },
    SET_CURRENT_PRESENTATION_INFO: function (state, data) {
      data.presentationId = state.currentPPTID;
      data.presentationName = state.currentPPTTitle;
      console.log(data, 'set');
    },
    // INIT_CURRENT_PRESENTATION_INFO: function (state) {
    //   state.currentPPTID = null;
    //   state.currentPPTTitle = null;
    // },
  },
  actions: {
    getCurrentPresenatationInfo: function ({ commit }, data) {
      commit('GET_CURRENT_PRESENTATION_INFO', data);
    },
    setCurrentPresenatationInfo: function ({ commit }, data) {
      commit('SET_CURRENT_PRESENTATION_INFO', data);
    },
    // initPresentationInfo: function ({ commit }) {
    //   commit('INIT_CURRENT_PRESENTATION_INFO');
    // },
  },
  getters: {
    getCurrentId(state) {
      return state.currentPPTID;
    },
    getCurrentTitle(state) {
      return state.currentPPTTitle;
    },
  },
};
