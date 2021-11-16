<template>
  <div></div>
</template>
<script src="https://cdn.jsdelivr.net/npm/@tensorflow/tfjs@1.3.1/dist/tf.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@teachablemachine/pose@0.8/dist/teachablemachine-pose.min.js"></script>

<script>
import store from '@/store';
import _ from 'lodash';

const URL = 'https://teachablemachine.withgoogle.com/models/sHKstMnIt/';
let model, webcam, ctx, labelContainer, maxPredictions;
export default {
  name: 'handcolntroler',
  props: {},
  data() {
    return {
      predictFlag: true,
    };
  },
  mounted() {
    this.init();
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
        await this.wait(3000);
      }
      window.requestAnimationFrame(this.loop);
    },

    async predict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element
      console.log('ready to predict');

      const { pose, posenetOutput } = await model.estimatePose(webcam.canvas);
      // Prediction 2: run input through teachable machine classification model
      const prediction = await model.predict(posenetOutput);

      if (prediction[1].probability.toFixed(2) > 0.99) {
        console.log('nextPage');
        this.predictFlag = false;
        store.dispatch('meetingRoom/goNext');
        //setTimeout(store.dispatch('meetingRoom/goNext'), 1000);
        //_.throttle(store.dispatch('meetingRoom/goNext'), 1000);

        // const classPrediction =
        //     prediction[i].className + ": " + prediction[i].probability.toFixed(2);
        //     labelContainer.childNodes[i].innerHTML = classPrediction;
      }
    },
  },
};
</script>

<style></style>
