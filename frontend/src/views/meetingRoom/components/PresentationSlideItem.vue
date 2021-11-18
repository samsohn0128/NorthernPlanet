<template>
  <div class="slide-container">
    <img
      :src="slideUrl"
      alt=""
      class="slide-insert img-fluid"
      @click="changeImg"
    />
  </div>
</template>

<script>
// import "./template.scss";

export default {
  name: 'PresentationSlideItem',
  components: {},
  // : props
  props: {
    slideUrl: String,
    Next: String,
  },
  // : data
  data() {
    return {};
  },
  // : computed
  computed: {},
  // : lifecycle hook
  mounted() {},
  // : methods
  methods: {
    changeImg() {
      if (this.Next == true) {
        this.emitNext();
      } else if (this.Next == false) {
        this.emitPrev();
      }
    },
    emitPrev() {
      this.$emit('selectPrev');
    },
    emitNext() {
      this.$emit('selectNext');
    },
  },
};
</script>

<style scoped>
.slide-container {
  position: relative;
  width: 100%;
  height: 200px;
  margin-bottom: 20px;
}
.slide-insert {
  border-radius: 25px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  width: 100%;
  height: 100%;
  cursor: pointer;
}
</style>
