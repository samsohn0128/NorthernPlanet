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
            class="btn btn-outline-dark"
            data-bs-dismiss="modal"
          >
            Cancel
            <!-- {{ data.presentationName }} -->
          </button>
          <button
            type="button"
            class="btn button-danger-setting"
            @click="modifyPPTName()"
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
  // : computed
  computed: {
    currentPPTTitle() {
      return this.$store.state.mypage.currentPPTTitle;
    },
  },
  watch: {
    currentPPTTitle: function () {
      console.log(
        'currentPPTTitle',
        this.presentationName,
        this.currentPPTTitle,
      );
      this.presentationName = this.currentPPTTitle;
    },
  },
  data() {
    return {
      presentationId: store.getters['mypage/getCurrentId'],
      presentationName: store.getters['mypage/getCurrentTitle'],
      // presentationId: this.id,
      // presentationName: this.name,
    };
  },
  // 모달창을 만들어서 발표 자료 이름을 먼저 입력받은 상태.
  methods: {
    async modifyPPTName() {
      this.presentationId = store.getters['mypage/getCurrentId'];
      // Error: modal 열 때마다 새로고침하지 않으면 아래 아이디, Name 맨 처음에 고른 값이 들어가는 에러 발생

      // let userData = {
      //   presentationId: this.presentationId,
      //   presentationName: this.presentationName,
      // };
      let userData = this.presentationName;
      // 제목이 비어있을 경우 튕겨내기
      if (userData === '') {
        this.$toastError('제목을 입력해주세요.');
        return;
      }
      try {
        await modifyPresentationName(this.presentationId, userData);
        await this.$toastSuccess('이름을 변경했습니다.');
        this.$router.go();
      } catch (exp) {
        console.log(exp);
        this.$toastError('이름 변경에 실패했습니다.');
      }
    },
    // getInfo() {
    //   this.presentationId = store.getters['mypage/getCurrentId'];
    //   this.presentationName = store.getters['mypage/getCurrentTitle'];
    // },
  },
  // mounted() {
  //   this.getInfo();
  // },
};
</script>

<style>
.button-danger-setting {
  background: #ba635f;
  color: white;
}
.button-danger-setting:hover {
  color: white;
}
</style>
