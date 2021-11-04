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
        <div>
          <label class="input-file-button" for="input-picture"
            >사진 추가하기</label
          >
          <label class="input-file-button" for="input-file"
            >파일 추가하기</label
          >
          <div id="showFileName"></div>
          <input
            type="file"
            multiple="multiple"
            id="input-picture"
            style="display: none"
            @change="selectFile"
          />
          <input
            type="file"
            id="input-file"
            style="display: none"
            @change="selectFile"
          />
          <img
            class="image"
            :src="imgUrl.first"
            alt=""
            @click="dialogVisible.first = true"
          />
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
            Add
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { addPresentation, addPptpdf } from '@/api/presentation.js';
import store from '@/store';

export default {
  name: 'AddPPTModal',
  data() {
    return {
      dialogVisible: { first: false },
      imgUrl: { first: '' },
      userId: store.getters['users/getUserId'],
    };
  },
  // 모달창을 만들어서 발표 자료 이름을 먼저 입력받은 상태.
  methods: {
    async addPPT() {
      let formData = new FormData();
      let imgFile = document.getElementById('input-picture').files;
      console.log(imgFile);
      if (imgFile.length == 0) {
        this.Pptpdf();
        return;
      }
      formData.append('userId', this.userId);
      for (let i = 0; i < imgFile.length; i++) {
        formData.append('slides', imgFile[i]);
      }
      try {
        await addPresentation(formData);
        await store.dispatch('mypage/setToastTrue', 1);
        await this.$router.go();
      } catch (exp) {
        this.$toastError('사진 추가에 실패했습니다.');
      }
    },
    async Pptpdf() {
      let formData = new FormData();
      let imgFile = document.getElementById('input-file').files;
      if (imgFile.length == 0) {
        this.$toastError('파일을 추가해주세요.');
        return;
      }
      formData.append('userId', this.userId);
      for (let i = 0; i < imgFile.length; i++) {
        formData.append('pptPdf', imgFile[i]);
      }
      try {
        await addPptpdf(formData);
        await store.dispatch('mypage/setToastTrue', 2);
        await this.$router.go();
      } catch (exp) {
        this.$toastError('프레젠테이션 추가에 실패했습니다.');
      }
    },
    selectFile(e) {
      const file = e.target.files[0];
      document.getElementsByClassName('image')[0].src =
        URL.createObjectURL(file);
      this.imageChanged = true;
      this.imgUrl.first = URL.createObjectURL(file);

      let imgFile = document.getElementById('input-file').files;
      if (imgFile.length == 0) {
        imgFile = document.getElementById('input-picture').files;
      }
      let fileList = '';
      for (let i = 0; i < imgFile.length; i++) {
        fileList += imgFile[i].name + '<br>';
      }
      let target2 = document.getElementById('showFileName');
      target2.innerHTML = fileList;
    },
    change() {
      // this.dialog = false;
      window.location.reload();
    },
  },
};
</script>

<style scoped>
.input-file-button {
  display: flex;
  justify-content: center;
  width: 30%;
  height: 10%;
  /* height: 40px; */
  background-color: purple;
  /* border-radius: 50%; */
  color: white;
  margin-right: 35px;
  cursor: pointer;
  font-size: 15px;
  font-weight: bold;
  text-align: center;
}
</style>
