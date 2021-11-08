<template>
  <div
    class="modal fade"
    id="AddSlideModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="ModalAddSlide"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h6 class="modal-title" id="ModalAdd">슬라이드 추가하기</h6>
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
          <div id="showFileName"></div>
          <input
            type="file"
            id="input-picture"
            style="display: none"
            @change="selectFile"
          />
          <img
            class="image thumbnail-setting"
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
import { addSlide } from '@/api/presentation.js';
import store from '@/store';

export default {
  name: 'AddSlideModal',
  data() {
    return {
      dialogVisible: { first: false },
      imgUrl: { first: '' },
      userId: store.getters['users/getUserId'],
      presentationId: this.$route.params.presentation_id,
    };
  },
  // 모달창을 만들어서 발표 자료 이름을 먼저 입력받은 상태.
  methods: {
    async addPPT() {
      let formData = new FormData();
      let imgFile = document.getElementById('input-picture').files;
      // console.log(imgFile);
      if (imgFile.length == 0) {
        return;
      }
      formData.append('userId', this.userId);
      formData.append('presentationId', this.presentationId);
      formData.append('slideFile', imgFile[0]);
      // formData 보기
      // for (let key of formData.keys()) {
      //   console.log('key: ', key);
      // }
      // for (let value of formData.values()) {
      //   console.log('value: ', value);
      // }
      try {
        await addSlide(formData);
        await this.$toastSuccess('사진을 추가했습니다.');
        this.$router.go();
      } catch (exp) {
        this.$toastError('사진 추가에 실패했습니다.');
      }
    },
    selectFile(e) {
      const file = e.target.files[0];
      document.getElementsByClassName('image')[0].src =
        URL.createObjectURL(file);
      this.imageChanged = true;
      this.imgUrl.first = URL.createObjectURL(file);
      let imgFile = document.getElementById('input-picture').files;
      // console.log(imgFile);
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
.thumbnail-setting {
  max-width: 200px;
  max-height: 150px;
}
</style>
