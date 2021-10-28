<template>
  <div
    class="modal fade"
    id="AddPPTModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="ModalChangePassword"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h6 class="modal-title" id="ModalWithdraw">발표자료 추가하기</h6>
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
          <form role="form text-left">
            <label>제목을 입력하세요.</label>
            <div class="input-group mb-3">
              <input
                type="title"
                class="form-control"
                placeholder="Title"
                aria-label="Title"
                aria-describedby="title-addon"
                v-model="presentationName"
              />
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn bg-gradient-secondary"
            data-bs-dismiss="modal"
          >
            Cancel
          </button>
          <button
            type="button"
            class="btn bg-gradient-danger"
            @click="addPPT()"
            data-bs-dismiss="modal"
          >
            Change
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { addpresentation } from '@/api/presentation.js';

export default {
  name: 'AddPPTModal',
  data() {
    return {
      presentationName: '',
    };
  },
  // 모달창을 만들어서 발표 자료 이름을 먼저 입력받은 상태.
  async addPPT() {
    let userData = {
      user_id: this.$store.state.users.login.userid,
      presentationName: this.presentationName,
    };
    try {
      await addpresentation(userData);
      this.$router.go();
    } catch (exp) {
      this.$alertify.error('프레젠테이션 추가에 실패했습니다.');
    }
  },
};
</script>

<style scoped></style>
