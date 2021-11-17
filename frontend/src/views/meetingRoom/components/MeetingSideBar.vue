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
      <button
        :class="[
          { 'navigator-button-active': handactive },
          { 'navigator-button-inactive': !handactive },
          'navigator-hand-button',
        ]"
        @click="handAct"
      >
        손짓
      </button>
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
    <Hand class="hand" v-model="handactive" />

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
import Hand from './Hand.vue';

export default {
  name: 'MeetingSideBar',
  components: { MeetingParticipants, PresentationController, Chat, Hand },
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
