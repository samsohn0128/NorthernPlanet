<template>
  <div class="card card-presentation move-on-hover">
    <div class="card-header p-0 mx-3 mt-3 position-relative z-index-1">
      <a href="javascript:;" class="d-block">
        <img
          src="https://images.unsplash.com/photo-1517303650219-83c8b1788c4c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=bd4c162d27ea317ff8c67255e955e3c8&auto=format&fit=crop&w=2691&q=80"
          class="img-fluid border-radius-lg"
        />
      </a>
    </div>

    <div class="card-body pt-4">
      <span
        class="
          text-gradient text-primary text-uppercase text-xs
          font-weight-bold
          my-2
        "
        >PPT or PDF or IMAGE</span
      >
      <div class="row">
        <a href="javascript:;" class="card-title h5 d-block text-darker col-8">
          파일이름
        </a>
        <i class="material-icons col" style="font-size: 30px">expand_more</i>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PresentationCardItem',
  components: {},
  data() {
    return {};
  },
  computed: {},
  methods: {},
};
</script>
<style>
.card-presentation {
  width: 200px;
  height: 220px;
}
.material-icons:hover {
  color: rgb(139, 0, 116);
}
</style>
