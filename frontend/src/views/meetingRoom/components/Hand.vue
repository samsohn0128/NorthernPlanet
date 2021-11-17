<template>
  <div></div>
</template>
<script src="https://cdn.jsdelivr.net/npm/@tensorflow/tfjs@1.3.1/dist/tf.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@teachablemachine/pose@0.8/dist/teachablemachine-pose.min.js"></script>

<script>
import store from '@/store';
import _ from 'lodash';

const URL = 'https://teachablemachine.withgoogle.com/models/-8b4izMaY/';
let model, webcam, ctx, labelContainer, maxPredictions;
export default {
  name: 'handcolntroler',
  props: {
    value: {
      default: false,
    },
  },
  data() {
    return {
      predictFlag: true,
      handactive: this.value,
      handactive2: this.value,
      isLoading: false,
    };
  },
  watch: {
    value: function () {
      this.handactive = this.value;
      this.handactive2 = this.value;
      if (this.handactive) {
        this.isLoading = true;
        this.init().then(() => {
          this.isLoading = false;
        });
      }
    },
    isLoading: function () {
      this.$emit('isLoading', this.isLoading);
    },
  },
  methods: {
    async init() {
      const modelURL = URL + 'model.json';
      const metadataURL = URL + 'metadata.json';

      // load the model and metadata
      // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
      // Note: the pose library adds a tmPose object to your window (window.tmPose)
      model = await tmPose.load(modelURL, metadataURL);
      maxPredictions = model.getTotalClasses();

      // Convenience function to setup a webcam
      const size = 200;
      const flip = true; // whether to flip the webcam
      webcam = new tmPose.Webcam(size, size, flip); // width, height, flip
      await webcam.setup(); // request access to the webcam
      await webcam.play();
      window.requestAnimationFrame(this.loop);
    },

    wait(timeToDelay) {
      return new Promise(() => {
        setTimeout(() => {
          this.predictFlag = true;
          window.requestAnimationFrame(this.loop);
        }, timeToDelay);
      });
    },

    async loop(timestamp) {
      webcam.update(); // update the webcam frame
      if (this.predictFlag) await this.predict();
      else {
        await this.wait(800);
      }
      if (this.handactive) window.requestAnimationFrame(this.loop);
    },

    async predict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element

      const { pose, posenetOutput } = await model.estimatePose(webcam.canvas);
      // Prediction 2: run input through teachable machine classification model
      const prediction = await model.predict(posenetOutput);
      if (prediction[0].probability.toFixed(2) > 0.99 && this.handactive2) {
      } else if (
        prediction[1].probability.toFixed(2) > 0.99 &&
        this.handactive2
      ) {
        this.predictFlag = false;
        if (
          this.$store.state.meetingRoom.now <
          this.$store.state.meetingRoom.imgLength - 1
        ) {
          store.dispatch('meetingRoom/goNext');
        }
      } else if (
        prediction[2].probability.toFixed(2) > 0.99 &&
        this.handactive2
      ) {
        this.predictFlag = false;
        if (this.$store.state.meetingRoom.size < 4) {
          this.$store.state.meetingRoom.size++;
          const message = {
            id: 'changePresentation',
            currentPage: this.$store.state.meetingRoom.currentPage,
            location: this.$store.state.meetingRoom.location,
            size: this.$store.state.meetingRoom.size,
            transition: this.$store.state.meetingRoom.transition,
          };
          this.$store.dispatch('meetingRoom/sendMessage', message);
        }
      } else if (
        prediction[3].probability.toFixed(2) > 0.99 &&
        this.handactive2
      ) {
        this.predictFlag = false;
        if (this.$store.state.meetingRoom.size > 0) {
          this.$store.state.meetingRoom.size--;
          const message = {
            id: 'changePresentation',
            currentPage: this.$store.state.meetingRoom.currentPage,
            location: this.$store.state.meetingRoom.location,
            size: this.$store.state.meetingRoom.size,
            transition: this.$store.state.meetingRoom.transition,
          };
          this.$store.dispatch('meetingRoom/sendMessage', message);
        }
      } else if (prediction[4].probability.toFixed(2) > 0.99) {
        this.predictFlag = false;
        this.handactive2 = !this.handactive2;
        this.$emit('gestureNotWorking');
      } else if (
        prediction[5].probability.toFixed(2) > 0.99 &&
        this.handactive2
      ) {
        this.predictFlag = false;
        this.$store.state.meetingRoom.location = 'right';
        const message = {
          id: 'changePresentation',
          currentPage: this.$store.state.meetingRoom.currentPage,
          location: this.$store.state.meetingRoom.location,
          size: this.$store.state.meetingRoom.size,
          transition: this.$store.state.meetingRoom.transition,
        };
        this.$store.dispatch('meetingRoom/sendMessage', message);
      } else if (
        prediction[6].probability.toFixed(2) > 0.99 &&
        this.handactive2
      ) {
        this.predictFlag = false;
        this.$store.state.meetingRoom.location = 'top';
        const message = {
          id: 'changePresentation',
          currentPage: this.$store.state.meetingRoom.currentPage,
          location: this.$store.state.meetingRoom.location,
          size: this.$store.state.meetingRoom.size,
          transition: this.$store.state.meetingRoom.transition,
        };
        this.$store.dispatch('meetingRoom/sendMessage', message);
      }
    },
  },
};
</script>

<style>
.gestureNotWorking {
  background: #ba635f;
}
</style>
