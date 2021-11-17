<template>
  <div class="meetingroom-container">
    <transition name="left-slide">
      <VideoUnitGroup class="left-side-bar" v-if="leftSideShow" />
    </transition>
    <div
      :class="[
        { 'main-left-margin': leftSideShow },
        { 'main-right-margin': rightSideShow },
        'main',
        'd-flex',
        'flex-column',
        'align-items-center',
        'justify-content-center',
      ]"
    >
      <!-- left side bar control buttons -->
      <!-- <transition name="button-show"
        ><img
          src="@/assets/icons/left.svg"
          alt="left side bar(participants videos) toggle button"
          :class="[{ 'button-left-margin': leftSideShow }, 'left-side-toggler']"
          v-if="leftSideShow"
          @click="toggleLeftSide"
      /></transition>
      <transition name="button-show"
        ><img
          src="@/assets/icons/right.svg"
          alt=""
          class="left-side-toggler"
          v-if="!leftSideShow"
          @click="toggleLeftSide"
      /></transition> -->
      <!-- left side bar control buttons -->
      <!-- Room Title -->
      <h1 class="room-title">{{ roomTitle }}</h1>
      <!-- <div class="upside-ppt-inside set-timer-location">
        <div class="time-space">
          <span id="showMin">00</span>
          :
          <span id="showSec">00</span>
          .
          <span id="showMilisec">00</span>
        </div>
        <div class="time-button-space">
          <button id="startButton" class="settingStart" @click="startButton">
            start
          </button>
          <button id="resetButton" class="settingReset" @click="resetButton">
            reset
          </button>
        </div>
      </div> -->
      <div class="upside-ppt-inside set-timer-location">
        <div class="time-space">
          <span id="showMin">00</span> : <span id="showSec">00</span>
          <span class="time-button-space">
            <!-- 시작 -->
            <i
              class="ni ni-button-play time-button"
              @click="startButton"
              v-if="!timerStart"
            ></i>
            <!-- 일시정지 -->
            <i
              class="ni ni-button-pause time-button"
              @click="startButton"
              v-if="timerStart"
            ></i>
            <!-- 초기화 -->
            <i class="ni ni-button-power time-button" @click="resetButton"></i>
          </span>
        </div>
      </div>
      <!-- Main Video -->
      <MainVideoUnit
        class="main-video-unit"
        :mainParticipant="mainParticipant"
        :key="mainParticipant.name"
      />
      <!-- Meeting Controller -->
      <MeetingController style="margin-top: 25px" />
      <!-- right side bar control buttons -->
      <transition name="button-show"
        ><img
          src="@/assets/icons/right.svg"
          alt="right side bar toggle button"
          :class="[
            { 'button-right-margin': rightSideShow },
            'right-side-toggler',
          ]"
          v-if="rightSideShow"
          @click="toggleRightSide"
      /></transition>
      <transition name="button-show"
        ><img
          src="@/assets/icons/left.svg"
          alt=""
          class="right-side-toggler"
          v-if="!rightSideShow"
          @click="toggleRightSide"
      /></transition>
      <!-- right side bar control buttons -->
    </div>
    <transition name="right-slide">
      <MeetingSideBar class="right-side-bar" v-if="rightSideShow" />
    </transition>
  </div>
</template>

<script>
import VideoUnitGroup from './components/VideoUnitGroup.vue';
import MainVideoUnit from './components/MainVideoUnit.vue';
import MeetingController from './components/MeetingController.vue';
import MeetingSideBar from './components/MeetingSideBar.vue';

import _ from 'lodash';

export default {
  name: 'MeetingRoom',
  components: {
    VideoUnitGroup,
    MainVideoUnit,
    MeetingController,
    MeetingSideBar,
  },
  // : props
  props: {},
  // : data
  data() {
    return {
      leftSideShow: true,
      rightSideShow: true,
    };
  },
  // : watch
  watch: {},
  // : computed
  computed: {
    participants() {
      return this.$store.state.meetingRoom.participants;
    },
    mainParticipant() {
      const mainParticipantName = Object.keys(this.participants).find(
        key => key === this.$store.state.meetingRoom.presenter,
      );
      return this.participants[mainParticipantName];
    },
    roomTitle() {
      return _.split(this.$store.state.meetingRoom.roomName, '-')[0];
    },
  },
  // : lifecycle hook
  mounted() {},
  // : methods
  methods: {
    toggleLeftSide: function () {
      this.leftSideShow = !this.leftSideShow;
    },
    toggleRightSide: function () {
      this.rightSideShow = !this.rightSideShow;
    },
    // 시작
    startButton() {
      // 시작 버튼을 누를 때
      if (this.timerStart == false) {
        this.timerStart = true;
        document.getElementById('startButton').innerText = 'pause';
        document.getElementById('startButton').style.background = '#ef6262';
        document.getElementById('startButton').style.borderColor = '#ef6262';
        // 0.001초마다 시간 갱신
        this.timerWork = setInterval(() => {
          let nowTime = new Date(Date.now() - this.stTime);

          this.min = this.addZero(nowTime.getMinutes());
          this.sec = this.addZero(nowTime.getSeconds());
          this.milisec = this.addZero(
            Math.floor(nowTime.getMilliseconds() / 10),
          );

          document.getElementById('showMin').innerText = this.min;
          document.getElementById('showSec').innerText = this.sec;
          document.getElementById('showMilisec').innerText = this.milisec;
        }, 1);
      } else {
        // 일시정지 버튼을 누를 때
        this.endTime = Date.now();
        this.timerStart = false;
        document.getElementById('startButton').innerText = 'start';
        document.getElementById('startButton').style.background = '#4aae71';
        document.getElementById('startButton').style.borderColor = '#4aae71';
        clearInterval(this.timerWork);
      }
      // 시간 체크
      if (!this.stTime) {
        this.stTime = Date.now();
      } else {
        this.stTime += Date.now() - this.endTime;
      }
    },
    // 리셋하기, 종료 버튼
    resetButton() {
      this.stTime = 0;
      this.min = 0;
      this.sec = 0;
      this.milisec = 0;
      this.endTime = Date.now();
      this.timerStart = false;
      document.getElementById('startButton').innerText = 'start';
      clearInterval(this.timerWork);
      this.timerWork = null;
      document.getElementById('showMin').innerText = '00';
      document.getElementById('showSec').innerText = '00';
      document.getElementById('showMilisec').innerText = '00';
    },
    // 계산
    addZero(num) {
      return num < 10 ? '0' + num : '' + num;
    },
  },
};
</script>

<style scoped>
.meetingroom-container {
  height: 100vh;
  width: 100vw;
  padding: 20px 20px;
  background: linear-gradient(90deg, #2c3153 0%, #15182a 100%);
}
.left-side-bar {
  background: none;
  height: 100vh;
  width: 250px;
  top: 0;
  left: 20px;
  position: fixed;
}
.main {
  background: none;
  width: auto;
  height: 100%;
}
.main-video-unit {
  margin-top: 25px;
  position: relative;
  height: 100%;
  width: auto;
}
.right-side-bar {
  width: 400px;
  top: 0;
  right: 0;
  height: 100vh;
  background: #e9ecef;
  position: fixed;
  overflow: auto;
}
.main-left-margin {
  margin-left: 255px;
  transition: 0.3s;
}
.main-right-margin {
  margin-right: 390px;
  transition: 0.3s;
}
.button-left-margin {
  margin-left: 255px;
}
.button-right-margin {
  margin-right: 390px;
}
.left-side-toggler {
  position: absolute;
  left: 0%;
  top: 50%;
  cursor: pointer;
  transform: translate(100%, -50%);
}
.right-side-toggler {
  position: absolute;
  right: 0%;
  top: 50%;
  cursor: pointer;
  transform: translate(-100%, -50%);
}
.room-title {
  color: white;
}
.left-slide-enter-active {
  animation: slideInLeft 0.3s;
}
.left-slide-leave-active {
  animation: slideOutLeft 0.3s;
}
.right-slide-enter-active {
  animation: slideInRight 0.3s;
}
.right-slide-leave-active {
  animation: slideOutRight 0.3s;
}
.button-show-enter-active {
  animation-delay: 0.3s;
  visibility: hidden;
}
::-webkit-scrollbar {
  display: none;
}

.upside-ppt-inside {
  width: 20vw;
  height: 15vh;
}
/* .set-timer-location {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}
.time-space {
  width: 12vw;
  text-align: center;
  background: rgb(222, 221, 226);
  color: black;
  font-size: 18px;
  padding: 5px;
  border-radius: 10px 10px 0px 0px;
} */
/* 스톱워치 */
.set-timer-location {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.time-space {
  width: 8vw;
  height: 2.3vw;
  text-align: center;
  background: #505753;
  color: white;
  font-size: 15px;
  padding: 5px;
  border-radius: 10px 10px 10px 10px;
}
.time-button-space {
  width: 8vw;
  text-align: center;
  background: #505753;
  color: white;
  font-size: 13px;
  padding: 5px;

  border-radius: 0px 0px 10px 10px;
}
.time-button {
  margin: 2px;
}
</style>
