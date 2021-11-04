export default {
  namespaced: true,
  state: () => ({
    // cuttentPPT: {
    //   Id: null,
    //   Title: null,
    // },
    currentPPTID: null,
    currentPPTTitle: null,
    effect: null,
    sequenceNum: null,
    getToastAlert: 0,
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
    SET_TOAST_TRUE: function (state, number) {
      state.getToastAlert = number;
    },
    SET_TOAST_FALSE: function (state) {
      state.getToastAlert = 0;
    },
  },
  actions: {
    setCurrentPresenatationInfo: function ({ commit }, data) {
      commit('SET_CURRENT_PRESENTATION_INFO', data);
    },
    // initPresentationInfo: function ({ commit }) {
    //   commit('INIT_CURRENT_PRESENTATION_INFO');
    // },
    setEffect({ commit }, effect) {
      commit('SET_EFFECT', effect);
    },
    setSequenceNum({ commit }, sequenceNum) {
      commit('SET_SEQUENCE_NUM', sequenceNum);
    },
    setToastTrue({ commit }, number) {
      commit('SET_TOAST_TRUE', number);
    },
    setToastFalse({ commit }) {
      commit('SET_TOAST_FALSE');
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
    getEffect(state) {
      return state.effect;
    },
    getSequenceNum(state) {
      return state.sequenceNum;
    },
  },
};
