<template>
  <div
    class="modal fade"
    id="AddPPTModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="ModalAddPPT"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered modal-md" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h6 class="modal-title" id="ModalAdd">Add new presentation</h6>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="cancelPPT()"
          >
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">
          <div class="modal-body-content container py-3">
            <div class="modal-body-content-item row">
              <label class="input-file-button col" for="input-picture">
                <div class="input-file-button-content col">
                  <img
                    src="https://img.icons8.com/ios/50/000000/image.png"
                    class="row"
                  />

                  <div class="row">IMAGE</div>
                </div>
              </label>

              <label class="input-file-button col" for="input-file">
                <div class="input-file-button-content col">
                  <img
                    src="https://img.icons8.com/ios/50/000000/file.png"
                    class="row"
                  />

                  <div class="row">FILE</div>
                </div>
              </label>
            </div>
            <div
              class="modal-body-file-list row"
              id="showFileName"
              v-if="this.imgFile"
            >
              <div class="col">{{ this.imgFile[0].name }}</div>
              <div class="col" v-if="this.imgFile.length > 1">
                + {{ this.imgFile.length - 1 }}
              </div>
            </div>
          </div>

          <input
            type="file"
            multiple="multiple"
            id="input-picture"
            style="display: none"
            accept=".jpg, .png, .jpeg"
            @change="selectImage"
          />
          <input
            type="file"
            id="input-file"
            style="display: none"
            accept=".gif, .pdf, .pptx"
            @change="selectFile"
          />
          <!-- <img
            class="image thumbnail-setting"
            :src="imgUrl.first"
            alt=""
            @click="dialogVisible.first = true"
          /> -->
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-outline-dark button-setting"
            data-bs-dismiss="modal"
            @click="cancelPPT()"
          >
            Cancel
          </button>
          <button
            type="button"
            class="btn button-setting button-color-setting"
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
      imgFile: null,
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
        await this.$toastSuccess('사진을 저장했습니다.');
        this.$router.go();
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
        await this.$toastSuccess('파일을 저장했습니다.');
        this.$router.go();
      } catch (exp) {
        this.$toastError('프레젠테이션 추가에 실패했습니다.');
      }
    },
    cancelPPT() {
      this.$router.go();
    },
    selectFile() {
      this.imgFile = null;
      this.imgFile = document.getElementById('input-file').files;
    },
    selectImage() {
      this.imgFile = null;
      this.imgFile = document.getElementById('input-picture').files;
    },
    change() {
      // this.dialog = false;
      window.location.reload();
    },
  },
};
</script>

<style scoped>
.modal-body {
  width: 100%;
  height: 100%;
}
.modal-body-content {
  width: 100%;
  height: 100%;
}
.modal-body-content-item {
  width: 100%;
  margin-left: 14px;
}
.modal-body-file-list {
  margin-left: 17px;
}
.input-file-button-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.input-file-button {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  width: 100%;
  height: 100%;
  /* height: 40px; */
  background-color: #b4c6a6;

  /* border-radius: 50%; */
  color: #ffffff;
  margin-right: 35px;
  cursor: pointer;
  font-size: 15px;
  font-weight: bold;
  text-align: center;
  border-radius: 10px;
}
.thumbnail-setting {
  max-width: 200px;
  max-height: 150px;
}
.button-color-setting {
  background: #66806a;
  color: white;
}
.button-danger-setting {
  background: #ba635f;
  color: white;
}
.button-setting {
  width: 80px;
  height: 40px;
  padding: 5px;
  margin: 0 5px 0 5px;
}
</style>
