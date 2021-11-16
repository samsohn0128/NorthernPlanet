<template>
  <div>
    <!-- navigator -->
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
        대본
      </button>
      <button class="navigator-question-button">?</button>
    </div>
    <!-- prev, next image -->
    <div class="text-center">
      <div class="small-img-container">
        <span>
          <img
            v-if="sidebaridx == 1"
            src="@/assets/presentationTemplates/first-slide.png"
            class="small-img-setting"
            alt="prev_image" />
          <img
            v-else-if="sidebaridx > 1"
            :src="slideList[sidebaridx - 1].slideFile"
            class="small-img-setting"
            alt="prev_image"
            @click="prevImage"
        /></span>
        <span>
          <img
            v-if="sidebaridx == slideList.length - 2"
            src="@/assets/presentationTemplates/last-slide.png"
            class="small-img-setting"
            alt="next_image" />
          <img
            v-else-if="sidebaridx < slideList.length - 2"
            :src="slideList[sidebaridx + 1].slideFile"
            class="small-img-setting"
            alt="next_image"
            @click="nextImage"
        /></span>
        <span></span>
      </div>

      <!-- Location/Size -->
      <div v-if="LocationSizeShow">
        <!-- Location -->
        <div>
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
            <h4>location</h4>
          </button>
        </div>
        <div>
          <div class="template-container">
            <div @click="selectRight" class="overlay">
              <img
                src="@/assets/presentationTemplates/presentation-right.jpg"
                alt=""
                :class="[
                  { 'insert-border': selectedLocation === 'right' },
                  'template-insert',
                  'img-fluid',
                ]"
              />
              <span v-if="selectedLocation === 'right'">selected</span>
              <span v-else>Presentation On Right</span>
            </div>
          </div>
          <div class="template-container">
            <div @click="selectLeft" class="overlay">
              <img
                src="@/assets/presentationTemplates/presentation-left.jpg"
                alt=""
                :class="[
                  { 'insert-border': selectedLocation === 'left' },
                  'template-insert',
                  'img-fluid',
                ]"
              />
              <span v-if="selectedLocation === 'left'">selected</span>
              <span v-else>Presentation On Left</span>
            </div>
          </div>
          <div class="template-container">
            <div @click="selectTop" class="overlay">
              <img
                src="@/assets/presentationTemplates/presentation-top.jpg"
                alt=""
                :class="[
                  { 'insert-border': selectedLocation === 'top' },
                  'template-insert',
                  'img-fluid',
                ]"
              />
              <span v-if="selectedLocation === 'top'">selected</span>
              <span v-else>Presentation On Top</span>
            </div>
          </div>
        </div>
        <!-- size -->
        <div>
          <h2>size</h2>
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
      <!-- Effect -->
      <div v-else>
        <div class="head-animation">
          <div
            v-for="(effect, index) in effects"
            :class="['effect-container']"
            :key="index"
            :id="index"
            @click="showExample(effect, index)"
          >
            <h6>{{ index }}</h6>
          </div>
        </div>
      </div>
      <div class="d-flex justify-content-center navigator">
        <button
          :class="[
            { 'button-setting': LocationSizeShow },
            { 'navigator-button-inactive': !LocationSizeShow },
            'navigator-Gesture-button',
          ]"
          @click="selectLocationSizeMenu"
        >
          Location/Size
        </button>
        <button
          :class="[
            { 'button-setting': !LocationSizeShow },
            { 'navigator-button-inactive': LocationSizeShow },
            'navigator-script-button',
          ]"
          @click="selectEffectMenu"
        >
          Effect
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MeetingSideBar',
  // : props
  props: {
    slideList: Array,
    idx: Number,
  },
  // : data
  data() {
    return {
      GestureShow: true,
      ScriptShow: true,
      LocationSizeShow: true,
      selectedSize: null,
      selectedLocation: null,
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
      sidebaridx: this.idx + 1,
    };
  },
  // : computed
  computed: {},
  // : watch
  watch: {
    selectedSize: function () {
      this.selectSize(this.selectedSize);
    },
    idx() {
      this.sidebaridx = this.idx;
    },
  },
  // : lifecycle hook
  mounted() {},
  // : methods
  methods: {
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
      this.emitScriptShow();
    },
    selectLocationSizeMenu() {
      this.LocationSizeShow = true;
    },
    selectEffectMenu() {
      this.LocationSizeShow = false;
    },
    selectSize: function (selectedSize) {
      this.selectedSize = selectedSize;
      this.emitSize();
    },
    selectRight: function () {
      this.selectedLocation = 'right';
      this.emitLocation();
    },
    selectLeft: function () {
      this.selectedLocation = 'left';
      this.emitLocation();
    },
    selectTop: function () {
      this.selectedLocation = 'top';
      this.emitLocation();
    },
    // Effect의 예시를 보여준다.
    showExample(idx, effect) {
      const el = document.getElementById(effect);
      el.classList.add(effect);
      setTimeout(function () {
        el.classList.remove(effect);
      }, 1000);
      // effect 저장

      this.emitEffect(idx);
    },
    prevImage() {
      if (this.sidebaridx > 1) {
        this.sidebaridx -= 1;
        this.emitImageminus();
      }
    },
    nextImage() {
      if (this.sidebaridx < this.slideList.length - 2) {
        this.sidebaridx += 1;
        this.emitImageplus();
      }
    },
    emitSize() {
      this.$emit('selectedSize', this.selectedSize);
    },
    emitLocation() {
      this.$emit('selectedLocation', this.selectedLocation);
    },
    emitImageminus() {
      this.$emit('selectIdxminus');
    },
    emitImageplus() {
      this.$emit('selectIdxplus');
    },
    emitScriptShow() {
      this.$emit('selectedShow');
    },
    emitEffect(effect) {
      this.$emit('selectedEffect', effect);
    },
  },
};
</script>

<style scoped>
.navigator {
  margin: 25px 0px;
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
/* .navigator-button-active {
  background: linear-gradient(90deg, #2c3153 0%, #15182a 100%);
} */
.navigator-button-inactive {
  background: linear-gradient(90deg, #a0b0d0 0%, #7587a6 100%);
}
.button-setting {
  background: #4ba3c7;
  color: white;
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
.small-img-container {
  margin-top: 10px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.small-img-setting {
  max-width: 190px;
  max-height: 100px;
  padding: 5px;
  cursor: pointer;
}
.border-setting {
  border: 0px;
}
.text-center {
  text-align: center;
}
.template-container {
  display: inline-block;
  justify-content: center;
  width: 240px;
  height: 180px;
  margin-top: 30px;
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
.template-container:hover .overlay {
  opacity: 1;
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

.effect-container {
  position: relative;
  cursor: pointer;
  margin: 1%;
  width: 200px;
  height: 80px;
  background: white;
  border-radius: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.head-animation {
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
  max-height: 833px;
}

/* 애니메이션 설정 */

.fadein {
  animation: fadeIn 0.7s;
}
.fadedown {
  animation: fadeInDown 0.7s;
}
.fadeleft {
  animation: fadeInLeft 0.7s;
}
.faderight {
  animation: fadeInRight 0.7s;
}
.fadeup {
  animation: fadeInUp 0.7s;
}
.backdown {
  animation: backInDown 0.7s;
}
.backup {
  animation: backInUp 0.7s;
}
.flipx {
  animation: flipInX 0.7s;
}
.flipy {
  animation: flipInY 0.7s;
}
.rotatein {
  animation: rotateIn 0.7s;
}
</style>
