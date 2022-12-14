<template>
  <div>
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
    <!-- now -->
    <div style="position: relative">
      <PresentationSlideItem :slideUrl="slideUrls[now]" />
      <div class="overlay"><span>Now</span></div>
    </div>
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
  </div>
</template>
<script>
import PresentationSlideItem from './PresentationSlideItem.vue';
import store from '@/store';

export default {
  name: 'PresentationSlider.vue',
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
    };
  },
  // : computed
  computed: {
    presentationSize() {
      return this.$store.state.meetingRoom.size;
    },
    presentationLocation() {
      return this.$store.state.meetingRoom.location;
    },
    slideUrls() {
      return this.$store.state.meetingRoom.imageSrcs;
    },
    transition() {
      return this.$store.state.meetingRoom.transition;
    },
    currentPage() {
      return this.$store.state.meetingRoom.now;
    },
    PagePlus() {
      return this.$store.state.meetingRoom.plus;
    },
    PageMinus() {
      return this.$store.state.meetingRoom.minus;
    },
  },
  // : watch
  watch: {
    PagePlus: function () {
      this.progressNext();
    },
    PageMinus: function () {
      this.progressPrev();
    },
    currentPage: function () {
      this.now = this.currentPage;
      this.prev = this.currentPage - 1;
      this.next = this.currentPage + 1;
    },
  },
  created() {
    this.now = this.$store.state.meetingRoom.now;
    this.prev = this.now - 1;
    this.next = this.now + 1;
  },
  // : lifecycle hook
  mounted() {},
  // : methods
  methods: {
    progressPrev: function () {
      if (this.$store.state.meetingRoom.now > 0) {
        store.dispatch('meetingRoom/goPrev');

        this.prev -= 1;
        this.now -= 1;
        this.next -= 1;
      } else {
        this.alertMessage = '첫 번째 슬라이드입니다.';
        this.activeAlert();
      }
    },
    progressNext: function () {
      if (
        this.$store.state.meetingRoom.now <
        this.$store.state.meetingRoom.imgLength - 1
      ) {
        store.dispatch('meetingRoom/goNext');

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
  },
};
</script>

<style scoped>
.progress-button {
  cursor: pointer;
}
.template-insert {
  border-radius: 25px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  width: 100%;
  margin-bottom: 20px;
  height: auto;
}
.slider-prev-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 125px;
  height: 35px;
  border-radius: 30px 0px 0px 30px;
  color: white;
  font-weight: bold;
  background: linear-gradient(90deg, #66806a 0%, #66806a 100%);
}
.slider-next-button {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 125px;
  height: 35px;
  border-radius: 0px 30px 30px 0px;
  color: white;
  font-weight: bold;
  background: linear-gradient(90deg, #66806a 0%, #66806a 100%);
}
.slider-progress-indicator {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border: none;
  width: 100px;
  height: 35px;
  color: white;
  font-weight: bold;
  background: linear-gradient(90deg, #aebdb6 0%, #aebdb6 100%);
  display: flex;
  justify-content: center;
  align-items: center;
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
  width: 350px;
  height: 200px;
  padding: 5px;
  cursor: pointer;
  margin-bottom: 20px;
  border-radius: 25px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
</style>
