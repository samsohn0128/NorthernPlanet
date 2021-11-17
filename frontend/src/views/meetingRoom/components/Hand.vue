<template>
  <div></div>
</template>
<script src="https://cdn.jsdelivr.net/npm/@tensorflow/tfjs@1.3.1/dist/tf.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@teachablemachine/pose@0.8/dist/teachablemachine-pose.min.js"></script>

<script>
import store from '@/store';
import _ from 'lodash';

//aeeun
// const URL = 'https://teachablemachine.withgoogle.com/models/sHKstMnIt/';
//dongwoo right hand only
const URL = 'https://teachablemachine.withgoogle.com/models/-8b4izMaY/';
let model, webcam, ctx, labelContainer, maxPredictions;
export default {
  name: 'handcolntroler',
  props: {},
  data() {
    return {
      predictFlag: true,
      mountFlag: false,
    };
  },
  mounted() {
    this.mountFlag = true;
    console.log('this.mountFlag: ' + this.mountFlag + ' - mounted()');
    this.init();
  },
  unmounted() {
    console.log('this.mountFlag: ' + this.mountFlag + ' - unmounted()');
    this.mountFlag = false;
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
        await this.wait(500);
      }
      if (this.mountFlag) window.requestAnimationFrame(this.loop);
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
        // if (this.selectedSize < 5) this.selectedSize++;
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
        // if (this.selectedSize > 1) this.selectedSize--;
      }
      //aeeun
      // if (prediction[1].probability.toFixed(2) > 0.99) {
      //   console.log('nextPage');
      //   // this.predictFlag = false;
      //   // store.dispatch('meetingRoom/goNext');
      //   //setTimeout(store.dispatch('meetingRoom/goNext'), 1000);
      //   //_.throttle(store.dispatch('meetingRoom/goNext'), 1000);

      //   // const classPrediction =
      //   //     prediction[i].className + ": " + prediction[i].probability.toFixed(2);
      //   //     labelContainer.childNodes[i].innerHTML = classPrediction;
      // } else if (prediction[2].probability.toFixed(2) > 0.99) {
      //   console.log('prediction[2]');
      // } else if (prediction[3].probability.toFixed(2) > 0.99) {
      //   console.log('prediction[3]');
      // } else if (prediction[0].probability.toFixed(2) > 0.99) {
      //   console.log('prediction[0]');
      // }
    },
  },
};
</script>

<style></style>
