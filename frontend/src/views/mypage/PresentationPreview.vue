<template>
  <div class="meetingroom-container">
    <transition name="left-slide"></transition>
    <div :class="[{ 'main-right-margin': rightSideShow }, 'main']">
      <div class="upside-ppt">
        <div class="upside-ppt-inside">
          <button @click="goBack()">뒤로가기</button>
        </div>
        <div class="upside-ppt-inside script-setting">
          <div class="move-sequence" @click="setIdx(idx - 1)">&lt;</div>
          <div v-if="slideList[idx].script == null" class="script-inside">
            대본을<br />설정해주세요.
          </div>
          <div v-else class="script-inside">{{ slideList[idx].script }}</div>
          <div class="move-sequence" @click="setIdx(idx + 1)">&gt;</div>
        </div>
        <div class="upside-ppt-inside set-timer-location">
          <div class="time-space">
            <span id="showMin">00</span>
            :
            <span id="showSec">00</span>
            .
            <span id="showMilisec">00</span>
          </div>
          <div class="time-button-space">
            <button id="startButton" @click="startButton">start</button>
            <button id="resetButton" @click="resetButton">reset</button>
          </div>
        </div>
      </div>

      <div id="ppt-image-setting">
        <img
          v-if="idx == 0"
          src="@/assets/presentationTemplates/first-slide.png"
          id="img-setting"
          alt="prev-image"
          class="size-4"
        />
        <img
          v-else-if="idx == slideList.length - 1"
          src="@/assets/presentationTemplates/last-slide.png"
          id="img-setting"
          alt="next-image"
          class="size-4"
        />
        <img
          v-else
          :src="slideList[idx].slideFile"
          id="img-setting"
          alt="current-slide"
          class="size-4"
        />
      </div>

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
        @selectIdx="setIdx"
      />
    </transition>
  </div>
</template>

<script>
import MeetingSideBar from './preview/MeetingSideBar.vue';
import { getPresentationDetail } from '@/api/presentation.js';
export default {
  name: 'PresentationPreview',
  components: {
    MeetingSideBar,
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
      milisec: '00', // ms 표시하기
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
  },
  // : lifecycle hook
  created() {
    this.getPresentationData();
    console.log(this.$route.params);
  },
  mounted() {
    document.addEventListener('keydown', e => {
      console.log(e);
      switch (e.key) {
        case 'ArrowLeft':
          if (this.idx > 1) {
            this.idx -= 1;
            this.setIdx(this.idx);
          }
          break;
        case 'ArrowRight':
          if (this.idx < this.slideList.length - 2) {
            this.idx += 1;
            this.setIdx(this.idx);
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
    setIdx(selectedIdx) {
      this.idx = selectedIdx;
      console.log(this.idx);
    },
    // 시작
    startButton() {
      // 시작 버튼을 누를 때
      if (this.timerStart == false) {
        this.timerStart = true;
        document.getElementById('startButton').innerText = 'pause';
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
    // 뒤로 가기
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
#ppt-image-setting {
  max-width: 60vw;
  height: 80vh;
  display: flex;
  align-items: center;
}
.meetingroom-container {
  height: 100vh;
  width: 100vw;
  padding: 20px 20px;
  background: linear-gradient(90deg, #dbecec 0%, #f165d3 100%);
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
.upside-ppt {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.upside-ppt-inside {
  width: 20vw;
  height: 15vh;
}
.script-setting {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.set-timer-location {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}
.time-space {
  width: 12vw;
  text-align: center;
  background: rgb(141, 235, 177);
  color: black;
  font-size: 18px;
  padding: 5px;
  border-radius: 10px 10px 0px 0px;
}
.time-button-space {
  width: 12vw;
  text-align: center;
  background: rgb(141, 235, 177);
  color: black;
  font-size: 18px;
  padding: 5px;
  border-radius: 0px 0px 10px 10px;
}
.script-inside {
  justify-content: center;
  text-align: center;
  background: gray;
  font: 24px bold;
  color: black;
}
.move-sequence {
  width: 32px;
  height: 32px;
  justify-content: center;
  text-align: center;
  background: gray;
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
  max-width: 12vw;
  max-height: 10vh;
}
.size-1 {
  max-width: 24vw;
  max-height: 20vh;
}
.size-2 {
  max-width: 36vw;
  max-height: 30vh;
}
.size-3 {
  max-width: 48vw;
  max-height: 40vh;
}
.size-4 {
  max-width: 60vw;
  max-height: 50vh;
}
.img-setting {
  max-width: 60vw;
  max-height: 50vh;
  margin: 5px;
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
.room-title {
  color: white;
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
::-webkit-scrollbar {
  display: none;
}
</style>
