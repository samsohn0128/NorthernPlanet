<template>
  <div class="meetingroom-container">
    <div
      :class="[
        { 'main-right-margin': rightSideShow },
        'main',
        'd-flex',
        'flex-column',
        'align-items-center',
        'justify-content-center',
      ]"
    >
      <div class="top-content">
        <!-- <div class="upside-ppt-inside">
          <button
            class="button-setting btn btn-sm btn-round mb-1 me-1"
            @click="goBack()"
          >
            뒤로가기
          </button>
          <button
            id="script-button-text
            class="button-setting btn btn-sm btn-round mb-1 me-1"
            @click="showScript()"
          >
            대본 숨기기
          </button>
        </div> -->
        <!-- <div id="script-show" class="upside-ppt-inside script-setting">
          <div class="move-sequence" @click="setIdxminus()">&lt;</div>
          <div v-if="idx == 1">
            <Viewer
              id="changeinitialValue"
              :initialValue="slideList[idx].script"
              class="script-inside"
            />
          </div>
          <div v-else class="script-inside">
            <Viewer
              id="changeinitialValue"
              :initialValue="slideList[idx].script"
            />
          </div>
          <div class="move-sequence" @click="setIdxplus()">&gt;</div>
        </div> -->

        <!-- 타이머 -->
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
              <i
                class="ni ni-button-power time-button"
                @click="resetButton"
              ></i>
            </span>
          </div>
        </div>
      </div>

      <div class="main-body-div">
        <div class="video-body-div">
          <!-- local video element -->

          <!-- <button
            type="button"
            class="btn btn-setting"
            :class="{
              'button-setting': isVideoOn,
              'bg-gradient-secondary': !isVideoOn,
            }"
            @click="videoOnOff"
          >
            <span
              class="fas fa-video"
              :class="{
                'fa-video': isVideoOn,
                'fa-video-slash': !isVideoOn,
              }"
            ></span>
          </button> -->

          <!-- local video element -->
          <video
            class="main-video-unit video-insert-setting"
            height="100%"
            :id="'local-video'"
            autoplay="true"
            poster="@/assets/img/logos/focus_camera4.jpg"
          ></video>
        </div>

        <!-- <div id="ppt-image-setting">
          <transition name="fade" mode="out-in" v-if="idx !== null">
            <img
              :src="imageSrcs"
              :key="imageSrcs"
              alt="presentation image"
              :class="[sizePreset, transitionPreset]"
              id="img-setting"
            />
          </transition>
        </div> -->
      </div>
      <div class="bottom-content">
        <div class="d-flex controller" @keyup.right="progressNext">
          <button class="controller-button mx-3" @click="goBack">
            <i class="ni ni-fat-remove ni-2x"></i>
          </button>
        </div>
      </div>
      <!-- right side bar control buttons -->
      <!-- <transition name="button-show"
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
      /></transition> -->
    </div>
    <!-- right side bar -->
    <transition name="right-slide">
      <MeetingSideBar
        class="right-side-bar"
        v-if="rightSideShow"
        :slideList="slideList"
        :idx="idx"
        @selectedLocation="setLocation"
        @selectedSize="setSize"
        @selectIdxplus="setIdxplus"
        @selectIdxminus="setIdxminus"
        @selectedShow="showScript"
        @selectedEffect="setEffect"
      />
    </transition>
  </div>
</template>

<script>
import MeetingSideBar from './preview/MeetingSideBar.vue';
import { getPresentationDetail } from '@/api/presentation.js';
// import { getRoom } from '@/api/rooms.js';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
//import { Viewer } from '@toast-ui/vue-editor';

export default {
  name: 'PresentationPreview',
  components: {
    MeetingSideBar,
    // Viewer,
  },
  data() {
    return {
      rightSideShow: true,
      presentationId: this.$route.params.presentationId,
      userId: this.$route.params.userId,
      slideList: [
        {
          effect: null,
          script: null,
          sequence: null,
          slideFile: null,
          slideId: null,
        },
      ],
      idx: 0,
      size: 2,
      selectedLocation: 'top',
      timerWork: null, // 타이머가 0.01초마다 돌아가는 곳
      stTime: 0, // 시작 시간
      endTime: 0, // 마지막 시간
      timerStart: false, // 타이머가 돌아가고 있는지 확인
      min: '00', // 분 표시하기
      sec: '00', // 초 표시하기
      // milisec: '00', // ms 표시하기

      userName: null,

      isMicOn: false,
      isVideoOn: true,

      content: null,
      effects: {
        default: 0,
        fadein: 1,
        fadedown: 2,
        fadeleft: 3,
        faderight: 4,
        fadeup: 5,
        backdown: 6,
        backup: 7,
        flipx: 8,
        flipy: 9,
        rotatein: 10,
      },
    };
  },
  // : watch
  watch: {},
  // : computed
  computed: {
    imageSrcs() {
      return this.slideList[this.idx].slideFile;
    },
    participants() {
      return this.$store.state.meetingRoom.participants;
    },
    mainParticipant() {
      const mainParticipantName = Object.keys(this.participants).find(
        key => key === this.$store.state.meetingRoom.presenter,
      );
      return this.participants[mainParticipantName];
    },

    // Local Video 관련
    transitionPreset() {
      return 'transition-' + this.slideList[this.idx].effect;
    },
    sizePreset() {
      return 'size-' + this.size;
    },
  },
  // : lifecycle hook
  created() {
    this.getPresentationData();
    this.playVideoFromCamera();
  },
  mounted() {
    document.addEventListener('keydown', e => {
      console.log(e);
      switch (e.key) {
        case 'ArrowLeft':
          if (this.idx > 1) {
            this.setIdxminus();
          }
          break;
        case 'ArrowRight':
          if (this.idx < this.slideList.length - 2) {
            this.setIdxplus();
          }
          break;
        case 'a':
        case 'A':
          this.setLocation('left');
          break;
        case 'd':
        case 'D':
          this.setLocation('right');
          break;
        case 'w':
        case 'W':
          this.setLocation('top');
          break;
        case '0':
          this.selectedSize = 0;
          this.setSize(this.selectedSize);
          break;
        case '1':
          this.selectedSize = 1;
          this.setSize(this.selectedSize);
          break;
        case '2':
          this.selectedSize = 2;
          this.setSize(this.selectedSize);
          break;
        case '3':
          this.selectedSize = 3;
          this.setSize(this.selectedSize);
          break;
        case '4':
          this.selectedSize = 4;
          this.setSize(this.selectedSize);
          break;
      }
    });
  },
  // : methods
  methods: {
    toggleRightSide: function () {
      this.rightSideShow = !this.rightSideShow;
    },
    async getPresentationData() {
      let response = await getPresentationDetail(
        this.userId,
        this.presentationId,
      );
      // ByteArray를 img로 변경
      let imgByteArray = await response.data.slideList;
      imgByteArray.forEach(element => {
        element.slideFile = 'data:image/png;base64,' + element.slideFile;
      });
      // slideList 대입
      this.slideList = await response.data.slideList;
      this.slideList.unshift({
        effect: null,
        script: null,
        sequence: -1,
        slideFile: '@/assets/presentationTemplates/first-slide.png',
        slideId: -1,
      });
      this.slideList.push({
        effect: null,
        script: null,
        sequence: -1,
        slideFile: '@/assets/presentationTemplates/last-slide.png',
        slideId: -2,
      });
      console.log('시작 slideList: ', this.slideList);
      this.idx = 1;
      this.content = this.slideList[1].script;
    },
    // Size 세팅
    setSize(selectedSize) {
      this.size = selectedSize;
      document
        .getElementById('img-setting')
        .setAttribute('class', 'size-' + selectedSize);
    },
    // 위치 세팅
    setLocation(selectedLocation) {
      this.selectedLocation = selectedLocation;
      document
        .getElementById('ppt-image-setting')
        .setAttribute('class', 'img-' + selectedLocation);
    },
    // PPT 인덱스번호 세팅
    setIdxplus() {
      if (this.idx < this.slideList.length - 2) {
        this.idx += 1;
        this.content = this.slideList[this.idx].script;
        if (this.content != null) {
          document.getElementById('changeinitialValue').innerHTML =
            this.content;
        } else {
          document.getElementById('changeinitialValue').innerHTML =
            '대본을<br>설정해주세요';
        }
      }
      // console.log(this.content);
    },
    setIdxminus() {
      if (this.idx > 1) {
        this.idx -= 1;
        this.content = this.slideList[this.idx].script;
        if (this.content != null) {
          document.getElementById('changeinitialValue').innerHTML =
            this.content;
        } else {
          document.getElementById('changeinitialValue').innerHTML =
            '대본을<br>설정해주세요';
        }
      }
      // console.log(this.content);
    },
    // effect 미리보기
    setEffect(idx) {
      this.slideList[this.idx].effect = idx;
      this.transitionPreset();
    },
    // 시작
    startButton() {
      // 시작 버튼을 누를 때
      if (this.timerStart == false) {
        this.timerStart = true;

        // 0.001초마다 시간 갱신
        this.timerWork = setInterval(() => {
          let nowTime = new Date(Date.now() - this.stTime);

          this.min = this.addZero(nowTime.getMinutes());
          this.sec = this.addZero(nowTime.getSeconds());
          // this.milisec = this.addZero(
          //   Math.floor(nowTime.getMilliseconds() / 10),
          // );

          document.getElementById('showMin').innerText = this.min;
          document.getElementById('showSec').innerText = this.sec;
          // document.getElementById('showMilisec').innerText = this.milisec;
        }, 1);
      } else {
        // 일시정지 버튼을 누를 때
        this.endTime = Date.now();
        this.timerStart = false;

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
      // this.milisec = 0;
      this.endTime = Date.now();
      this.timerStart = false;
      clearInterval(this.timerWork);
      this.timerWork = null;
      document.getElementById('showMin').innerText = '00';
      document.getElementById('showSec').innerText = '00';
      // document.getElementById('showMilisec').innerText = '00';
    },
    // 계산
    addZero(num) {
      return num < 10 ? '0' + num : '' + num;
    },
    // 뒤로 가기
    goBack() {
      this.$router.go(-1);
    },
    showScript() {
      let scriptshow = document.getElementById('script-show');

      if (scriptshow.style.display != 'none') {
        scriptshow.style.display = 'none';
        document.getElementById('script-button-text').innerText = '대본 보이기';
      } else {
        scriptshow.style.display = 'flex';
        document.getElementById('script-button-text').innerText = '대본 숨기기';
      }
    },
    // videoOnOff: function () {
    //   if (this.isVideoOn) {
    //     this.isVideoOn = false;
    //     this.stopVideoFromCamera();
    //   } else {
    //     this.isVideoOn = true;
    //     this.playVideoFromCamera();
    //   }
    // },
    playVideoFromCamera: async function () {
      try {
        const constraints = { video: true, audio: false };
        const stream = await navigator.mediaDevices.getUserMedia(constraints);
        const videoElement = document.getElementById('local-video');
        videoElement.srcObject = stream;
      } catch (error) {
        console.error('Error opening video camera.', error);
      }
    },
    stopVideoFromCamera: async function () {
      try {
        const videoElement = document.getElementById('local-video');
        var stream = videoElement.srcObject;
        var tracks = stream.getTracks();

        for (var i = 0; i < tracks.length; i++) {
          var track = tracks[i];
          track.stop();
        }
        videoElement.srcObject = null;
      } catch (err) {
        console.error('Error stop video camera.', err);
      }
    },
  },
};
</script>

<style scoped>
#ppt-image-setting {
  margin-top: 57px;
  max-width: 100%;
  z-index: 1;
  width: 70%;
  height: 60vh;
  display: flex;
  align-items: center;
  position: absolute;
}
.meetingroom-container {
  height: 100vh;
  width: 100vw;
  padding: 20px 20px;
  /* background: linear-gradient(90deg, #2c3153 0%, #15182a 100%); */
  /* background: #66806a; */
  background: #2d382f;
}
/* RGB
93 244 237 #5df4ec
dbecec

253 206 30 #fdce1e */
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

/* 비디오 */
.main-video-unit {
  position: relative;
  height: 100%;
  width: auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.main-body-div {
  width: 100%;
  height: 90%;
  position: relative;
  display: flex;
  justify-content: center;
  align-content: center;
}
.video-body-div {
  position: absolute;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
}

.top-content {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  height: 5%;
  width: 71%;
  margin-bottom: 10px;
}

.bottom-content {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  height: 5%;
  margin-top: 10px;
}

.controller-button {
  background: linear-gradient(90deg, #f10488 0%, #a51bb2 100%);
  box-shadow: 0px 4px 4px black;
  border: none;
  width: auto;
  height: 40px;
  border-radius: 30px;
  color: white;
  font-weight: bold;
}
.controller-button-disabled {
  background: linear-gradient(90deg, #a0b0d0 0%, #7587a6 100%);
  box-shadow: 0px 4px 4px black;
  border: none;
  width: 150px;
  height: 40px;
  border-radius: 30px;
  color: white;
  font-weight: bold;
}
/* .upside-ppt-inside {
  width: 20vw;
  height: 15vh;
} */
.script-setting {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 50vw;
}
.button-setting {
  background: #4ba3c7;
  color: white;
}

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

.settingStart {
  background: #4aae71;
  border-color: #4aae71;
  width: 65px;
  border-radius: 20px;
  box-shadow: 0.5px 0.5px 1px;
}
.settingReset {
  background: #fbad10;
  border-color: #fbad10;
  width: 65px;
  border-radius: 20px;
  box-shadow: 0.5px 0.5px 1px;
}
.main-body-div {
  position: relative;
  display: flex;
  justify-content: center;
}
.webrtc-body-div {
  position: absolute;
  width: 70vw;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.btn-setting {
  width: 20%;
}
.script-inside {
  display: flex;
  justify-content: center;
  width: 40vw;
  height: 120px;
  overflow: auto;
  background: #e8f5e9;
  border-radius: 10px;
  padding: 5px;
  font: 16x bold;
  color: black;
}
.move-sequence {
  width: 32px;
  height: 32px;
  justify-content: center;
  text-align: center;
  background: rgb(222, 221, 226);
  color: black;
  font-size: 20px;
  cursor: pointer;
}
.downside-ppt {
  display: flex;
  flex-direction: row;
}
.img-top {
  display: flex;
  justify-content: center;
  align-items: flex-start !important;
}
/* .img-left {
  display: flex;
  justify-content: flex-start;
  align-items: center;
} */
.img-right {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.size-0 {
  margin: 2%;
  height: 30%;
}
.size-1 {
  margin: 2%;
  height: 40%;
}
.size-2 {
  margin: 2%;
  height: 50%;
}
.size-3 {
  margin: 2%;
  height: 60%;
}
.size-4 {
  margin: 2%;
  height: 100%;
}
.img-setting {
  max-width: 100%;
  max-height: 100%;
  margin: 5px;
}
.video-insert-setting {
  transform: scaleX(-1);
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
.main-right-margin {
  margin-right: 390px;
  transition: 0.3s;
}
/* .button-left-margin {
  margin-left: 255px;
} */
.button-right-margin {
  margin-right: 390px;
}
/* .left-side-toggler {
  position: absolute;
  left: 0%;
  top: 50%;
  cursor: pointer;
  transform: translate(100%, -50%);
} */
.right-side-toggler {
  position: absolute;
  right: 0%;
  top: 50%;
  cursor: pointer;
  transform: translate(-100%, -50%);
  border: solid black;
  background: black;
}
/* .left-slide-enter-active {
  animation: slideInLeft 0.3s;
}
.left-slide-leave-active {
  animation: slideOutLeft 0.3s;
} */
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

/* animation */
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
</style>
