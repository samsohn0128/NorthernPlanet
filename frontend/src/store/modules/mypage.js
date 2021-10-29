export default {
  namespaced: true,
  state: () => ({
    currentPage: null,
    location: null,
    size: null,
    transition: null,
  }),
  mutations: {},
  actions: {
    // 웹소켓으로 메시지 발신 action
    // sendMessage(context, message) {
    //   var jsonMessage = JSON.stringify(message);
    //   console.log('Sending message: ' + jsonMessage);
    //   context.state.ws.send(jsonMessage);
    // },
  },
  getters: {},
};
