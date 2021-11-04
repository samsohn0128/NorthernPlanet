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
        <div class="modal-body">
          <input
            id="url-input"
            :value="roomLink"
            readonly
            v-autowidth="{
              maxWidth: '960px',
              minWidth: '20px',
              comfortZone: 0,
            }"
            class="w-80"
          />
          <button @click="copyUrl" class="btn btn-link ml-auto">Copy</button>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-link ml-auto"
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
      this.$toast.success('URL copied!', {
        timeout: 2000,
        draggable: false,
        position: 'bottom-right',
        pauseOnFocusLoss: false,
        pauseOnHover: false,
      });
    },
  },
};
</script>
