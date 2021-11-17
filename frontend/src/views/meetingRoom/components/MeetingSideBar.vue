<template>
  <div>
    <!-- navigator -->
    <div class="d-flex justify-content-center navigator fixed-navigator">
      <button
        :class="[
          { 'navigator-button-active': participantShow },
          { 'navigator-button-inactive': !participantShow },
          'navigator-participant-button',
        ]"
        @click="selectParticipantMenu"
      >
        Participants
      </button>
      <button
        :class="[
          { 'navigator-button-active': presentationShow },
          { 'navigator-button-inactive': !presentationShow },
          'navigator-presentation-button',
        ]"
        @click="selectPresentationMenu"
      >
        Presentation
      </button>
      <button
        :class="[
          { 'navigator-button-active': chatShow },
          { 'navigator-button-inactive': !chatShow },
          'navigator-chat-button',
        ]"
        @click="selectChatMenu"
      >
        Chat
      </button>
      <!-- <button
        :class="[
          { 'navigator-button-active': handactive },
          { 'navigator-button-inactive': !handactive },
          'navigator-hand-button',
        ]"
        @click="handAct"
        v-show="!gestureLoading"
      >
        손짓
      </button>
      <LoadingSpinner v-show="gestureLoading" color="#15182a"></LoadingSpinner> -->
    </div>
    <div class="d-flex justify-content-center navigator">
      <button
        :class="[
          { 'button-toggle-setting': handactive && !gestureLoading },
          { 'navigator-toggle-inactive': gestureLoading },
          { 'navigator-toggle-inactive': !handactive },
          'navigator-Gesture-button',
        ]"
        @click="handAct"
      >
        <div v-if="!gestureLoading">Gesture</div>
        <LoadingSpinner
          v-show="gestureLoading"
          class="load-spinner"
          color="#15182a"
        ></LoadingSpinner>
      </button>
      <!-- <LoadingSpinner
        v-show="gestureLoading"
        class="load-spinner"
        color="#15182a"
      ></LoadingSpinner> -->
      <button
        :class="[
          { 'button-toggle-setting': ScriptShow },
          { 'navigator-toggle-inactive': !ScriptShow },
          'navigator-script-button',
        ]"
        @click="selectScriptMenu"
      >
        Script
      </button>
      <!-- <button class="navigator-question-button">?</button> -->
    </div>
    <!-- navigator -->
    <!-- SideBar Items -->
    <MeetingParticipants v-if="participantShow" class="meeting-participants" />
    <PresentationController
      v-if="presentationShow"
      class="presentation-controller"
    />
    <Chat v-if="chatShow" :messageList="messageList" class="chat" />
    <!-- <Hand v-if="handactive" class="hand" /> -->
    <Hand class="hand" v-model="handactive" @isLoading="changeGestureLoading" />

    <!-- SideBar Items -->
    <!-- access alert -->
    <transition name="fade">
      <div v-if="alertShow" class="access-alert">
        <h5 class="mb-0">발표자만 사용할 수 있습니다.</h5>
      </div>
    </transition>
  </div>
</template>

<script>
import MeetingParticipants from './MeetingParticipants.vue';
import PresentationController from './PresentationController.vue';
import LoadingSpinner from 'vue-spinner/src/PulseLoader.vue';

import Chat from './Chat.vue';
import Hand from './Hand.vue';

export default {
  name: 'MeetingSideBar',
  components: {
    MeetingParticipants,
    PresentationController,
    Chat,
    Hand,
    LoadingSpinner,
  },
  // : props
  props: {},
  // : data
  data() {
    return {
      participantShow: true,
      presentationShow: false,
      chatShow: false,
      alertShow: false,
      handactive: false,
      gestureLoading: false,
      ScriptShow: true,
    };
  },
  // : computed
  computed: {
    myName() {
      return this.$store.state.meetingRoom.myName;
    },
    presenter() {
      return this.$store.state.meetingRoom.presenter;
    },
    user() {
      return this.$store.getters['users/getUser'];
    },
    chat() {
      return this.$store.getters['meetingRoom/getChat'];
    },
    messageList() {
      return this.$store.getters['meetingRoom/getMessageList'];
    },
  },
  watch: {
    chat(value) {
      console.log('watch chat', value);

      this.$store.dispatch('meetingRoom/addChatMessage', value);
    },
    messageList(value) {
      console.log('watch messageList', value);
    },
  },
  // : lifecycle hook
  mounted() {},
  // : methods
  methods: {
    selectParticipantMenu: function () {
      this.participantShow = true;
      this.presentationShow = false;
      this.chatShow = false;
    },
    selectPresentationMenu: function () {
      if (this.myName === this.presenter) {
        this.participantShow = false;
        this.presentationShow = true;
        this.chatShow = false;
      } else {
        this.activateAlert();
      }
    },
    selectChatMenu: function () {
      this.participantShow = false;
      this.presentationShow = false;
      this.chatShow = true;
    },
    activateAlert: function () {
      this.alertShow = true;
      setTimeout(this.inactivateAlert, 2000);
    },
    inactivateAlert: function () {
      this.alertShow = false;
    },
    handAct: function () {
      this.handactive = !this.handactive;
    },
    changeGestureLoading: function (isLoading) {
      this.gestureLoading = isLoading;
    },
    selectScriptMenu: function () {
      if (this.ScriptShow == false) {
        this.ScriptShow = true;
      } else {
        this.ScriptShow = false;
      }
      // 대본을 키고 끄는 코드
      // this.emitScriptShow();
    },
  },
};
</script>

<style scoped>
.navigator {
  margin-top: 25px;
}
.navigator-participant-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 130px;
  height: 35px;
  border-radius: 30px 0px 0px 30px;
  color: white;
  font-weight: bold;
}
.navigator-presentation-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 130px;
  height: 35px;
  border-radius: 0px;
  color: white;
  font-weight: bold;
}
.navigator-chat-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 130px;
  height: 35px;
  border-radius: 0px 30px 30px 0px;
  color: white;
  font-weight: bold;
}
.navigator-Gesture-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  margin-right: 5px;
  width: 150px;
  height: 35px;
  border-radius: 30px 30px 30px 30px;
  color: white;
  font-weight: bold;
}
.navigator-script-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 150px;
  height: 35px;
  border-radius: 30px 30px 30px 30px;
  color: white;
  font-weight: bold;
}
.navigator-question-button {
  margin-left: 5px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 35px;
  height: 35px;
  border-radius: 30px;
  color: black;
  font-weight: bold;
  background: linear-gradient(90deg, #a0b0d0 0%, #7587a6 100%);
}
.button-toggle-setting {
  background: #959663;
  color: white;
}
.navigator-button-active {
  /* background: linear-gradient(90deg, #2c3153 0%, #15182a 100%); */
  /* background: #66806a;
  color: white; */
  background: #66806a;
  color: white;
}
.navigator-button-inactive {
  /* background: linear-gradient(90deg, #a0b0d0 0%, #7587a6 100%); */
  border: 2px solid #66806a;
  color: #456357;
}
.navigator-toggle-inactive {
  /* background: linear-gradient(90deg, #a0b0d0 0%, #7587a6 100%); */
  border: 2px solid #959663;
  color: #71734b;
}
.meeting-participants {
  margin: 25px 25px;
}
.presentation-controller {
  margin: 30px 25px 0;
}
.chat {
  margin: 25px 25px;
}
.access-alert {
  position: absolute;
  left: 50%;
  top: 7%;
  width: 75%;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.774);
  color: black;
  display: flex;
  justify-content: center;
  align-items: center;
  transform: translate(-50%);
  transition: 0.5s;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
/* 사이드바 상단바 고정 css*/
/* .fixed-navigator {
  position: fixed;
  top: 0;
} */
.load-spinner {
  z-index: 5;
}
</style>
