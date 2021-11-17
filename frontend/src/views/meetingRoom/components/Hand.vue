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
    };
  },
  mounted() {
    // this.init();
    console.log('value: ' + this.value + ' - mounted');
    console.log('handactive: ' + this.handactive + ' - mounted');
  },
  watch: {
    value: function () {
      this.handactive = this.value;
      console.log('value: ' + this.value);
      console.log('handactive: ' + this.handactive);
      if (this.handactive) {
        this.init();
      }
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
          console.log('after wait, this.predictFlag: ' + this.predictFlag);
          window.requestAnimationFrame(this.loop);
        }, timeToDelay);
      });
    },

    async loop(timestamp) {
      webcam.update(); // update the webcam frame
      if (this.predictFlag) await this.predict();
      else {
        console.log('before wait, this.predictFlag: ' + this.predictFlag);
        await this.wait(800);
      }
      if (this.handactive) window.requestAnimationFrame(this.loop);
    },

    async predict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element
      console.log('ready to predict');

      const { pose, posenetOutput } = await model.estimatePose(webcam.canvas);
      // Prediction 2: run input through teachable machine classification model
      const prediction = await model.predict(posenetOutput);
      if (prediction[0].probability.toFixed(2) > 0.99) {
        console.log('prediction[0]00000000000000000000');
      } else if (prediction[1].probability.toFixed(2) > 0.99) {
        console.log('prediction[1]111111111111111111111111');
        this.predictFlag = false;
        if (
          this.$store.state.meetingRoom.now <
          this.$store.state.meetingRoom.imgLength - 1
        ) {
          store.dispatch('meetingRoom/goNext');
        }
      } else if (prediction[2].probability.toFixed(2) > 0.99) {
        console.log('prediction[2]222222222222222222222222');
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
      } else if (prediction[3].probability.toFixed(2) > 0.99) {
        console.log('prediction[3]33333333333333333333333');
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
        console.log('prediction[4]4444444444444444444444444');
        this.predictFlag = false;
        this.$store.state.meetingRoom.location = 'left';
        const message = {
          id: 'changePresentation',
          currentPage: this.$store.state.meetingRoom.currentPage,
          location: this.$store.state.meetingRoom.location,
          size: this.$store.state.meetingRoom.size,
          transition: this.$store.state.meetingRoom.transition,
        };
        this.$store.dispatch('meetingRoom/sendMessage', message);
      } else if (prediction[5].probability.toFixed(2) > 0.99) {
        console.log('prediction[5]55555555555555555555555555');
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
      } else if (prediction[6].probability.toFixed(2) > 0.99) {
        console.log('prediction[6]666666666666666666666666');
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

<style></style>
