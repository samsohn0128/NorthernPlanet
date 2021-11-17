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
    </div>
    <div class="d-flex justify-content-center navigator">
      <button
        :class="[
          { 'button-setting': GestureShow },
          { 'navigator-button-inactive': !GestureShow },
          'navigator-Gesture-button',
        ]"
        @click="selectGestureMenu"
      >
        Gesture
      </button>
      <button
        :class="[
          { 'button-setting': ScriptShow },
          { 'navigator-button-inactive': !ScriptShow },
          'navigator-script-button',
        ]"
        @click="selectScriptMenu"
      >
        Script
      </button>
      <button class="navigator-question-button">?</button>
    </div>
    <!-- navigator -->
    <!-- SideBar Items -->
    <MeetingParticipants v-if="participantShow" class="meeting-participants" />
    <PresentationController
      v-if="presentationShow"
      class="presentation-controller"
    />
    <Chat v-if="chatShow" :messageList="messageList" class="chat" />

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
import Chat from './Chat.vue';

export default {
  name: 'MeetingSideBar',
  components: { MeetingParticipants, PresentationController, Chat },
  // : props
  props: {},
  // : data
  data() {
    return {
      participantShow: true,
      presentationShow: false,
      chatShow: false,
      alertShow: false,
      GestureShow: true,
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
    selectGestureMenu: function () {
      if (this.GestureShow == true) {
        this.GestureShow = false;
        // 제스처 끌 때 코드
      } else {
        this.GestureShow = true;
        // 제스처 킬 때 코드
      }
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
  width: 150px;
  height: 35px;
  border-radius: 30px 0px 0px 30px;
  color: white;
  font-weight: bold;
}
.navigator-script-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 150px;
  height: 35px;
  border-radius: 0px 30px 30px 0px;
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
.button-setting {
  background: #4ba3c7;
  color: white;
}
.navigator-button-active {
  background: linear-gradient(90deg, #2c3153 0%, #15182a 100%);
}
.navigator-button-inactive {
  background: linear-gradient(90deg, #a0b0d0 0%, #7587a6 100%);
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
</style>
