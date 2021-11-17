<template>
  <div
    :class="[locationPreset, 'main-video-container']"
    id="main-video-container"
  >
    <!-- <transition :name="transitionPreset" v-if="currentPage !== null"> -->
    <transition name="fade" mode="out-in" v-if="currentPage !== null">
      <img
        :src="imageSrcs[currentPage]"
        :key="imageSrcs[currentPage]"
        alt="presentation image"
        :class="[sizePreset, transitionPreset, 'img-fluid']"
        id="presentation-image"
      />
    </transition>
    <div
      v-html="scriptList[currentPage]"
      class="script"
      v-if="currentPage !== null"
    >
      {{ scriptList[currentPage] }}
    </div>
    <div class="overlay" style="z-index: 5">
      <span>{{ mainParticipantName }}</span>
    </div>
  </div>
  <!-- presentation image -->
  <!-- </div> -->
</template>

<script>
// import "./template.scss";
import _ from 'lodash';

export default {
  name: 'MainVideoUnit',
  components: {},
  // : props
  props: {
    mainParticipant: Object,
  },
  // : data
  data() {
    return {};
  },
  // : computed
  computed: {
    imageSrcs: function () {
      return this.$store.state.meetingRoom.imageSrcs;
    },
    scriptList: function () {
      return this.$store.state.meetingRoom.scriptList;
    },
    effectList: function () {
      return this.$store.state.meetingRoom.effectList;
    },
    currentPage: function () {
      return this.$store.state.meetingRoom.currentPage;
    },
    mainVideo() {
      return this.mainParticipant.getVideoElement();
    },
    mainParticipantName() {
      return _.split(this.mainParticipant.name, '-')[0];
    },
    // locationPreset, sizePreset
    locationPreset() {
      return 'presentation-' + this.$store.state.meetingRoom.location;
    },
    sizePreset() {
      return 'presentation-' + this.$store.state.meetingRoom.size;
    },
    transitionPreset() {
      return 'transition-' + this.effectList[this.currentPage];
    },
    /* presetCss() {
      return (
        'presentation-image-' +
        this.$store.state.meetingRoom.location +
        '-' +
        this.$store.state.meetingRoom.size
      );
    }, */
  },
  // Effect 실시간 반영을 위한 시도.. 실패..
  // watch: {
  //   '$store.state.meetingRoom.effectList'(dd, old) {
  //     console.log(dd, old);
  //     console.log('watch effectList');
  //     this.transitionPreset();
  //   },
  // },
  mounted() {
    this.mainVideo.classList.add('video-insert');
    document.getElementById('main-video-container').appendChild(this.mainVideo);
  },
  // : methods
  methods: {},
};
</script>

<style scoped>
/* .main-video-container {
  position: relative;
  height: 100%;
  width: auto;
} */
.main-video-container {
  position: relative;
  height: 100%;
  width: auto;
  display: flex;
  overflow: hidden;
}
.overlay {
  position: absolute;
  bottom: 5%;
  left: 50%;
  transform: translate(-50%);
  background: rgba(0, 0, 0, 0.7);
  width: 20%;
  height: 7%;
  transition: 0.3s ease;
  opacity: 0;
  color: white;
  font-size: 1.5rem;
  line-height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 5;
}
.main-video-container:hover .overlay {
  opacity: 1;
}
/* script */
.script {
  position: absolute;
  top: 5%;
  left: 50%;
  transform: translate(-50%);
  background: rgba(0, 0, 0, 0.4);
  width: 80%;
  height: 10%;
  transition: 0.3s ease;
  color: white;
  font-size: 1.5rem;
  line-height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 5;
  overflow: auto;
  border-radius: 10px;
}
/* scroll bar*/
/* 스크롤바 설정*/
::-webkit-scrollbar {
  width: 5px;
}

/* 스크롤바 막대 설정*/
::-webkit-scrollbar-thumb {
  height: 5px;
  background-color: #303f9f;
  /* 스크롤바 둥글게 설정    */
  border-radius: 10px;
}

/* 스크롤바 뒷 배경 설정*/
::-webkit-scrollbar-track {
  background-color: rgba(0, 0, 0, 0.33);
}
/* location presets */
.presentation-right {
  justify-content: flex-end;
  align-items: center;
}
.presentation-left {
  justify-content: start;
  align-items: center;
}
.presentation-top {
  justify-content: center;
  align-items: start;
}
/* size presets */
.presentation-0 {
  position: absolute;
  margin: 2%;
  height: 30%;
  z-index: 1;
}
.presentation-1 {
  position: absolute;
  margin: 2%;
  height: 40%;
  z-index: 1;
}
.presentation-2 {
  position: absolute;
  margin: 2%;
  height: 50%;
  z-index: 1;
}
.presentation-3 {
  position: absolute;
  margin: 2%;
  height: 60%;
  z-index: 1;
}
.presentation-4 {
  position: absolute;
  top: 0;
  height: 100%;
  border-radius: 25px;
  z-index: 1;
}
/* presentation preset css : 프레젠테이션 위치, 크기 프리셋 */
/* right */
/* .presentation-image-right-0 {
  position: absolute;
  right: 2%;
  top: 50%;
  height: 30%;
  transform: translate(0, -50%);
}
.presentation-image-right-1 {
  position: absolute;
  right: 2%;
  top: 50%;
  height: 40%;
  transform: translate(0, -50%);
}
.presentation-image-right-2 {
  position: absolute;
  right: 2%;
  top: 50%;
  height: 50%;
  transform: translate(0, -50%);
}
.presentation-image-right-3 {
  position: absolute;
  right: 2%;
  top: 50%;
  height: 60%;
  transform: translate(0, -50%);
}
.presentation-image-right-4 {
  position: absolute;
  top: 0;
  height: 100%;
  border-radius: 25px;
} */
/* left */
/* .presentation-image-left-0 {
  position: absolute;
  left: 2%;
  top: 50%;
  height: 30%;
  transform: translate(0, -50%);
}
.presentation-image-left-1 {
  position: absolute;
  left: 2%;
  top: 50%;
  height: 40%;
  transform: translate(0, -50%);
}
.presentation-image-left-2 {
  position: absolute;
  left: 2%;
  top: 50%;
  height: 50%;
  transform: translate(0, -50%);
}
.presentation-image-left-3 {
  position: absolute;
  left: 2%;
  top: 50%;
  height: 60%;
  transform: translate(0, -50%);
}
.presentation-image-left-4 {
  position: absolute;
  top: 0;
  height: 100%;
  border-radius: 25px;
} */
/* top */
/* .presentation-image-top-0 {
  position: absolute;
  left: 50%;
  top: 3%;
  height: 30%;
  transform: translate(-50%);
}
.presentation-image-top-1 {
  position: absolute;
  left: 50%;
  top: 3%;
  height: 40%;
  transform: translate(-50%);
}
.presentation-image-top-2 {
  position: absolute;
  left: 50%;
  top: 3%;
  height: 50%;
  transform: translate(-50%);
}
.presentation-image-top-3 {
  position: absolute;
  left: 50%;
  top: 3%;
  height: 60%;
  transform: translate(-50%);
}
.presentation-image-top-4 {
  position: absolute;
  top: 0;
  height: 100%;
  border-radius: 25px;
} */
/* transitions */
.transition-0 {
  animation: default 0.7s;
}
.transition-1 {
  animation: fadeIn 0.7s;
}
.transition-2 {
  animation: fadeInDown 0.7s;
}
.transition-3 {
  animation: fadeInLeft 0.7s;
}
.transition-4 {
  animation: fadeInRight 0.7s;
}
.transition-5 {
  animation: fadeInUp 0.7s;
}
.transition-6 {
  animation: backInDown 0.7s;
}
.transition-7 {
  animation: backInUp 0.7s;
}
.transition-8 {
  animation: flipInX 0.7s;
}
.transition-9 {
  animation: flipInY 0.7s;
}
.transition-10 {
  animation: rotateIn 0.7s;
}

.transition-default-enter-active {
  animation: default 0.7s;
}
.transition-fadein-enter-active {
  animation: fadeIn 0.7s;
}
.transition-fadedown-enter-active {
  animation: fadeInDown 0.7s;
}
.transition-fadeleft-enter-active {
  animation: fadeInLeft 0.7s;
}
.transition-faderight-enter-active {
  animation: fadeInRight 0.7s;
}
.transition-fadeup-enter-active {
  animation: fadeInUp 0.7s;
}
.transition-backdown-enter-active {
  animation: backInDown 0.7s;
}
.transition-backup-enter-active {
  animation: backInUp 0.7s;
}
.transition-flipx-enter-active {
  animation: flipInX 0.7s;
}
.transition-flipy-enter-active {
  animation: flipInY 0.7s;
}
.transition-rotatein-enter-active {
  animation: rotateIn 0.7s;
}
</style>

<style>
.video-insert {
  height: 100%;
  border-radius: 25px;
  box-shadow: 0px 4px 4px black;
  transform: scale(-1, 1);
}
</style>
