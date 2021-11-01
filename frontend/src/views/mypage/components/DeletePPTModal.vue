<template>
  <div
    class="modal fade"
    id="DeletePPTModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="DeletePPTModal"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h6 class="modal-title" id="ModalWithdraw">발표자료 삭제하기</h6>
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
          <div class="py-3 text-center">
            <i class="ni ni-bell-55 ni-3x"></i>
            <h4 class="text-gradient text-danger mt-4">
              정말 발표자료를 삭제하시겠습니까?
            </h4>
          </div>
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
            @click="deletePPT()"
            data-bs-dismiss="modal"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { deletePresentation } from '@/api/presentation.js';
import store from '@/store';
// import { mapState } from 'vuex';

export default {
  name: 'DeletePPTModal',
  data() {
    return {
      presentationId: null,
    };
  },
  // 모달창을 만들어서 발표 자료 이름을 먼저 입력받은 상태.
  methods: {
    async deletePPT() {
      // Error: modal 열 때마다 새로고침하지 않으면 아래 아이디도 맨 처음에 고른 id가 들어가는 에러 발생
      console.log(this.presentationId);
      try {
        await deletePresentation(this.presentationId);
      } catch (exp) {
        this.$alertify.error('프레젠테이션 삭제에 실패했습니다.');
      }
    },
    getInfo() {
      this.presentationId = store.getters['mypage/getCurrentId'];
    },
  },
  mounted() {
    this.getInfo();
  },
};
</script>

<style scoped></style>
