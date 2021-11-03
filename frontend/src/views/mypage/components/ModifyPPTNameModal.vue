<template>
  <div
    class="modal fade"
    id="ModifyPPTNameModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="ModalChangePPTNAme"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h6 class="modal-title" id="ModalWithdraw">발표자료 이름 수정하기</h6>
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
                placeholder="presentationName"
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
            @click="modifyPPT()"
            data-bs-dismiss="modal"
          >
            Modify
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { modifyPresentationName } from '@/api/presentation.js';
import store from '@/store';
// import { mapState } from 'vuex';

export default {
  name: 'ModifyPPTNameModal',
  props: { data: Object },
  data() {
    return {
      // presentationId: store.state.mypage.currentPPTID,
      // presentationName: store.state.mypage.currentPPTTitle,
      presentationId: this.data.currentPPTID,
      presentationName: this.data.currentPPTTitle,
    };
  },
  // 모달창을 만들어서 발표 자료 이름을 먼저 입력받은 상태.
  methods: {
    async modifyPPT() {
      // console.log(this.data);
      // console.log(this.presentationId);
      // console.log(this.presentationName);
      // Error: modal 열 때마다 새로고침하지 않으면 아래 아이디, Name 맨 처음에 고른 값이 들어가는 에러 발생
      let userData = {
        presentationId: this.presentationId,
        presentationName: this.presentationName,
      };
      // 제목이 비어있을 경우 튕겨내기
      if (userData.presentationName === '') {
        this.$alertify.error('제목을 입력해주세요.');
        return;
      }
      try {
        console.log(userData);
        await modifyPresentationName(this.presentationId, userData);
      } catch (exp) {
        this.$alertify.error('프레젠테이션 추가에 실패했습니다.');
      }
    },
    getInfo() {
      this.presentationId = store.getters['mypage/getCurrentId'];
      this.presentationName = store.getters['mypage/getCurrentTitle'];
    },
  },
  mounted() {
    this.getInfo();
  },
};
</script>

<style scoped></style>
