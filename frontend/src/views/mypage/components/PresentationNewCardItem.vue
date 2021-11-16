<template>
  <div class="card card-presentation move-on-hover">
    <div
      class="
        card-setting card-body
        p-0
        mx-3
        mt-3
        mb-3
        mg-fluid
        border-radius-lg
        bg-gradient-primary
        z-index-1
      "
    >
      <div class="new-presentation">
        <h2 class="text-white"><i class="fas fa-plus"></i></h2>
        <h4 class="text-white">new</h4>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PresentationCardItem',

  data() {
    return {};
  },
  computed: {},
  methods: {},
};
</script>
<style scoped>
.card-presentation {
  width: 200px;
  height: 220px;
}
.new-presentation {
  text-align: center;
}
.card-setting {
  background: linear-gradient(360deg, #98ddc1, #d8da90);
}
.card-body {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
