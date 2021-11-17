<template>
  <div
    class="modal fade"
    id="modal-link"
    tabindex="-1"
    role="dialog"
    aria-labelledby="modal-link"
    aria-hidden="true"
  >
    <div
      class="modal-dialog modal- modal-dialog-centered modal-"
      role="document"
    >
      <div class="modal-content">
        <div class="modal-header">
          <h6 class="modal-title" id="modal-title-default">
            Copy this room link and share it
          </h6>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          >
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body url-div input-group">
          <input
            id="url-input"
            :value="roomLink"
            readonly
            v-autowidth="{
              maxWidth: '960px',
              minWidth: '20px',
              comfortZone: 0,
            }"
            class="form-control w-80"
          />
          <button
            @click="copyUrl"
            class="btn button-color-setting button-setting btn-link ml-auto"
          >
            Copy
          </button>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn ml-auto btn-outline-dark button-setting"
            data-bs-dismiss="modal"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'LinkModal',
  props: { roomId: String },
  components: {},
  data() {
    return {
      roomLink: 'https://' + location.host + '/dashboard/' + this.roomId,
    };
  },
  methods: {
    copyUrl() {
      const copyText = document.getElementById('url-input');
      copyText.select();
      document.execCommand('copy');
      this.$toastSuccess('URL copied!');
    },
  },
};
</script>
<style scoped>
.button-color-setting {
  background: #66806a;
  color: white;
}
.button-outline-setting {
  border: 1.5px solid #66806a;
  color: #456357;
}
.button-danger-setting {
  background: #ba635f;
  color: white;
}
.button-setting {
  width: 70px;
  height: 40px;
  padding: 5px;
  margin: 0 10px 0 10px;
}
.url-div {
  margin: 5px 5px 15px;
}
</style>
