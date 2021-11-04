<template>
  <div
    class="modal fade"
    id="AddPPTPictureModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="ModalChangePassword"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h6 class="modal-title" id="ModalWithdraw">사진 등록하기</h6>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          >
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <label class="input-file-button" for="input-file">파일 추가하기</label>
        <div id="showFileName"></div>
        <input
          type="file"
          multiple="multiple"
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
import { presentationAddDelete } from '@/api/presentation.js';
import store from '@/store';

export default {
  name: 'AddPPTPictureModal',
  data() {
    return {
      dialogVisible: { first: false },
      imgUrl: { first: '' },
    };
  },
  // 모달창을 만들어서 발표 자료 이름을 먼저 입력받은 상태.
  methods: {
    async addPPT() {
      let formData = new FormData();
      let imgFile = document.getElementById('input-file').files;
      formData.append('file', imgFile);
      // formData.append('user_id', store.getters['users/getUser'].userId);
      formData.append('user_id', store.state.users.userId);
      formData.append('slideId', this.$route.params.presentation_id);
      formData.append('sequenceNum', store.state.mypage.sequenceNum);
      // let userData = {
      //   user_id: store.getters['users/getUser'].userId,
      //   formData,
      // };
      try {
        await presentationAddDelete(formData);
      } catch (exp) {
        this.$alertify.error('파일 추가에 실패했습니다.');
      }
    },
    selectFile(e) {
      const file = e.target.files[0];
      document.getElementsByClassName('image')[0].src =
        URL.createObjectURL(file);
      this.imageChanged = true;
      this.imgUrl.first = URL.createObjectURL(file);

      let imgFile = document.getElementById('input-file').files;
      let fileList = '';
      for (let i = 0; i < imgFile.length; i++) {
        fileList += imgFile[i].name + '<br>';
      }
      let target2 = document.getElementById('showFileName');
      target2.innerHTML = fileList;
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
