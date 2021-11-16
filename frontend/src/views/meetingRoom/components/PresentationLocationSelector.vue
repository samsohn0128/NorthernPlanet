<template>
  <div class="d-flex flex-column justify-content-center align-items-center">
    <div class="small-img-container">
      <!-- prev -->
      <PresentationSlideItem
        v-if="prev >= 0"
        :slideUrl="slideUrls[prev]"
        :Next="false"
        @selectPrev="progressPrev"
        @selectNext="progressNext"
      />
      <img
        v-else
        src="@/assets/presentationTemplates/first-slide.png"
        alt=""
        class="small-img-setting"
      />
      <!-- next -->
      <PresentationSlideItem
        v-if="next < slideUrls.length"
        :slideUrl="slideUrls[next]"
        :Next="true"
        @selectPrev="progressPrev"
        @selectNext="progressNext"
      />
      <img
        v-else
        src="@/assets/presentationTemplates/last-slide.png"
        alt=""
        class="small-img-setting"
      />
    </div>
    <!-- slider: 슬라이드 넘기기 ui -->
    <div class="d-flex justify-content-center align-items-center mb-5">
      <button class="slider-prev-button" @click="progressPrev">prev</button>
      <button @keyup.right="progressNext" @keyup.left="progressPrev">
        <div class="slider-progress-indicator">
          {{ now + 1 }}/{{ slideUrls.length }}
        </div>
      </button>
      <button class="slider-next-button" @click="progressNext">next</button>
    </div>
    <!-- slider: 슬라이드 넘기기 ui -->
    <!-- slider alert: 첫 슬라이드, 마지막 슬라이드 alert -->
    <transition name="fade">
      <div v-if="alertShow" class="access-alert">
        <h5 class="mb-0">{{ alertMessage }}</h5>
      </div>
    </transition>
    <!-- slider alert: 첫 슬라이드, 마지막 슬라이드 alert -->
    <div class="button-setting">
      <!-- location -->
      <button
        class="border-setting"
        @keyup.up="selectTop"
        @keyup.right="selectRight"
        @keyup.left="selectLeft"
        @keyup.49="selectSize(0)"
        @keyup.50="selectSize(1)"
        @keyup.51="selectSize(2)"
        @keyup.52="selectSize(3)"
        @keyup.53="selectSize(4)"
      >
        <h2 class="text-center">location</h2>
      </button>
      <div class="template-container">
        <img
          src="@/assets/presentationTemplates/presentation-right.jpg"
          alt=""
          :class="[
            { 'insert-border': presentationLocation === 'right' },
            'template-insert',
            'img-fluid',
          ]"
        />
        <div @click="selectRight" class="overlay">
          <span v-if="presentationLocation === 'right'">selected</span>
          <span v-else>Presentation On Right</span>
        </div>
      </div>
      <div class="template-container">
        <img
          src="@/assets/presentationTemplates/presentation-left.jpg"
          alt=""
          :class="[
            { 'insert-border': presentationLocation === 'left' },
            'template-insert',
            'img-fluid',
          ]"
        />
        <div @click="selectLeft" class="overlay">
          <span v-if="presentationLocation === 'left'">selected</span>
          <span v-else>Presentation On Left</span>
        </div>
      </div>
      <div class="template-container">
        <img
          src="@/assets/presentationTemplates/presentation-top.jpg"
          alt=""
          :class="[
            { 'insert-border': presentationLocation === 'top' },
            'template-insert',
            'img-fluid',
          ]"
        />
        <div @click="selectTop" class="overlay">
          <span v-if="presentationLocation === 'top'">selected</span>
          <span v-else>Presentation On Top</span>
        </div>
      </div>
      <!-- size -->
      <div class="size-controller">
        <h2 class="text-center">size</h2>
        <input
          type="range"
          class="range-select"
          min="0"
          max="4"
          step="1"
          v-model="selectedSize"
        />
      </div>
    </div>
  </div>
</template>

<script>
// import "./template.scss";
import PresentationSlideItem from './PresentationSlideItem.vue';

export default {
  name: 'PresentationLocationSelector',
  components: { PresentationSlideItem },
  // : props
  props: {},
  // : data
  data() {
    return {
      prev: null,
      now: null,
      next: null,
      alertMessage: null,
      alertShow: false,
      selectedSize: null,
      selectedLocation: null,
    };
  },
  // : computed
  computed: {
    slideUrls() {
      return this.$store.state.meetingRoom.imageSrcs;
    },
    transition() {
      return this.$store.state.meetingRoom.transition;
    },
    currentPage() {
      return this.$store.state.meetingRoom.currentPage;
    },
    presentationSize() {
      return this.$store.state.meetingRoom.size;
    },
    presentationLocation() {
      return this.$store.state.meetingRoom.location;
    },
    presentationTransition() {
      return this.$store.state.meetingRoom.transition;
    },
  },
  // : watch
  watch: {
    now: function () {
      // 발표자의 현재 이미지 url state에 저장: 이미지 변경 시 -> actions / mutation으로 분리해야함
      this.$store.state.meetingRoom.currentPage = this.now;
      // 현재 본인이 발표자라면 웹소켓 메시지 보내기
      if (
        this.$store.state.meetingRoom.presenter ===
        this.$store.state.meetingRoom.myName
      ) {
        var message = {
          id: 'changePresentation',
          currentPage: this.now,
          location: this.presentationLocation,
          size: this.presentationSize,
          transition: this.transition,
        };
        this.$store.dispatch('meetingRoom/sendMessage', message);
      }
    },
    selectedSize: function () {
      const message = {
        id: 'changePresentation',
        currentPage: this.currentPage,
        location: this.presentationLocation,
        size: this.selectedSize,
        transition: this.presentationTransition,
      };
      this.$store.dispatch('meetingRoom/sendMessage', message);
    },
  },
  // : lifecycle hook
  mounted() {
    this.prev = this.currentPage - 1;
    this.now = this.currentPage;
    this.next = this.currentPage + 1;
    // 매번 들어 올때, location, size를 state에 설정된 값으로 설정
    this.selectedSize = this.presentationSize;
    this.selectedLocation = this.presentationLocation;
  },
  // : methods
  methods: {
    progressPrev: function () {
      if (this.now > 0) {
        this.prev -= 1;
        this.now -= 1;
        this.next -= 1;
      } else {
        this.alertMessage = '첫 번째 슬라이드입니다.';
        this.activeAlert();
      }
    },
    progressNext: function () {
      if (this.now < this.slideUrls.length - 1) {
        this.prev += 1;
        this.now += 1;
        this.next += 1;
      } else {
        this.alertMessage = '마지막 슬라이드입니다.';
        this.activeAlert();
      }
    },
    activeAlert: function () {
      this.alertShow = true;
      setTimeout(this.inactivateAlert, 2000);
    },
    inactivateAlert: function () {
      this.alertShow = false;
    },
    selectSize: function (size) {
      this.selectedSize = size;
    },
    selectRight: function () {
      this.selectedLocation = 'right';
      const message = {
        id: 'changePresentation',
        currentPage: this.currentPage,
        location: this.selectedLocation,
        size: this.presentationSize,
        transition: this.presentationTransition,
      };
      this.$store.dispatch('meetingRoom/sendMessage', message);
    },
    selectLeft: function () {
      this.selectedLocation = 'left';
      const message = {
        id: 'changePresentation',
        currentPage: this.currentPage,
        location: this.selectedLocation,
        size: this.presentationSize,
        transition: this.presentationTransition,
      };
      this.$store.dispatch('meetingRoom/sendMessage', message);
    },
    selectTop: function () {
      this.selectedLocation = 'top';
      const message = {
        id: 'changePresentation',
        currentPage: this.currentPage,
        location: this.selectedLocation,
        size: this.presentationSize,
        transition: this.presentationTransition,
      };
      this.$store.dispatch('meetingRoom/sendMessage', message);
    },
  },
};
</script>

<style scoped>
.progress-button {
  cursor: pointer;
}

.slider-prev-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 125px;
  height: 35px;
  border-radius: 30px 0px 0px 30px;
  color: white;
  font-weight: bold;
  background: linear-gradient(90deg, #2c3153 0%, #15182a 100%);
}
.slider-next-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 125px;
  height: 35px;
  border-radius: 0px 30px 30px 0px;
  color: white;
  font-weight: bold;
  background: linear-gradient(90deg, #2c3153 0%, #15182a 100%);
}
.slider-progress-indicator {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 100px;
  height: 35px;
  color: white;
  font-weight: bold;
  background: linear-gradient(90deg, #a0b0d0 0%, #7587a6 100%);
  display: flex;
  justify-content: center;
  align-items: center;
}
.button-setting {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.border-setting {
  border: 0px;
}
.overlay {
  position: absolute;
  bottom: 0;
  border-radius: 25px;
  background: rgba(0, 0, 0, 0.7);
  width: 100%;
  height: 100%;
  opacity: 1;
  color: white;
  font-size: 2rem;
  line-height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.access-alert {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 75%;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.774);
  color: black;
  display: flex;
  justify-content: center;
  align-items: center;
  transform: translate(-50%, -50%);
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
.small-img-container {
  margin-top: 10px;
  margin-bottom: 10px;
  width: 350px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
.small-img-setting {
  max-width: 190px;
  max-height: 100px;
  padding: 5px;
  cursor: pointer;
}

.template-container {
  position: relative;
  width: 240px;
  height: 180px;
  margin-bottom: 20px;
  cursor: pointer;
}
.template-insert {
  border-radius: 25px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  width: 240px;
  height: 180px;
}
.insert-border {
  border: 0.4rem solid;
}
.overlay {
  position: absolute;
  bottom: 0;
  border-radius: 25px;
  background: rgba(255, 255, 255, 0.8);
  width: 240px;
  height: 180px;
  transition: 0.3s ease;
  opacity: 0;
  color: black;
  font-size: 1.2rem;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
}
.template-container:hover .overlay {
  opacity: 1;
}
.size-controller {
  margin-top: 0px;
}
.range-select {
  -webkit-appearance: none;
  appearance: none;
  width: 240px;
  height: 15px;
  border-radius: 5px;
  background: #d3d3d3;
  outline: none;
  opacity: 0.7;
  -webkit-transition: 0.2s;
  transition: opacity 0.2s;
}
.range-select:hover {
  opacity: 1;
}
.range-select::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background: #15182a;
  cursor: pointer;
}
.range-select::-moz-range-thumb {
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background: #15182a;
  cursor: pointer;
}
</style>
