export default {
  namespaced: true,
  state: () => ({
    // cuttentPPT: {
    //   Id: null,
    //   Title: null,
    // },
    currentPPTID: null,
    currentPPTTitle: null,
    sequenceNum: null,
  }),
  mutations: {
    SET_CURRENT_PRESENTATION_INFO: function (state, data) {
      state.currentPPTID = data.presentationId;
      state.currentPPTTitle = data.presentationName;
    },
    // INIT_CURRENT_PRESENTATION_INFO: function (state) {
    //   state.currentPPTID = null;
    //   state.currentPPTTitle = null;
    // },
    SET_EFFECT: function (state, effect) {
      state.effect = effect;
    },
    SET_SEQUENCE_NUM: function (state, sequenceNum) {
      state.sequenceNum = sequenceNum;
    },
    SET_CURRENT_PRESENTATION_TITLE: function (state, title) {
      state.currentPPTTitle = title;
    },
  },
  actions: {
    setCurrentPresenatationInfo: function ({ commit }, data) {
      commit('SET_CURRENT_PRESENTATION_INFO', data);
    },
    // initPresentationInfo: function ({ commit }) {
    //   commit('INIT_CURRENT_PRESENTATION_INFO');
    // },
    setSequenceNum({ commit }, sequenceNum) {
      commit('SET_SEQUENCE_NUM', sequenceNum);
    },
    setCurrentPresentationTitle({ commit }, title) {
      commit('SET_CURRENT_PRESENTATION_TITLE', title);
    },
  },
  getters: {
    // getCurrentId, getCurrentTitle 묶을 수 있을 듯.
    getCurrentId(state) {
      return state.currentPPTID;
    },
    getCurrentTitle(state) {
      return state.currentPPTTitle;
    },
    getSequenceNum(state) {
      return state.sequenceNum;
    },
  },
};
