<template>
  <div>
    <div class="card card-presentation z-index-1">
      <div
        class="card-header p-0 mx-3 mt-3 position-relative move-on-hover"
        @click="goToModifyPresentation()"
      >
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
            dropdown
          "
          >PPT or PDF or IMAGE</span
        >

        <div class="row z-index: 5 dropdown">
          <a
            href="javascript:;"
            class="card-title h5 d-block text-darker col-8"
            @click="goToModifyPresentation()"
          >
            {{ presentationInfo.presentationName }}
          </a>
          <button
            class="col"
            type="button"
            id="dropdownMenuButton"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            <i class="ni ni-bold-down z-index: 5"></i>
          </button>
          <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <li>
              <a
                class="dropdown-item"
                href="#ModifyPPTNameModal"
                data-bs-toggle="modal"
                >이름 변경</a
              >
            </li>
            <li>
              <a
                class="dropdown-item"
                href="#DeletePPTModal"
                data-bs-toggle="modal"
                >삭제</a
              >
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PresentationCardItem',

  props: { presentationInfo: Object },
  data() {
    return {};
  },
  computed: {},
  methods: {
    goToModifyPresentation() {
      this.$router.push({
        name: 'ModifyPresentation',
        params: {
          presentation_id: this.presentationInfo.presentationId,
          name: this.presentationInfo.presentationName,
        },
      });
    },
  },
};
</script>
<style>
.card-presentation {
  width: 200px;
  height: 220px;
}
.ni:hover {
  color: rgb(139, 0, 116);
}
</style>
