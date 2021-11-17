<template>
  <div>
    <div
      class="card card-presentation z-index-1"
      @click="changeInfo(presentationInfo)"
    >
      <div
        class="card-header p-0 mx-3 mt-3 position-relative move-on-hover"
        @click="goToModifyPresentation()"
      >
        <a href="javascript:;" class="d-block">
          <img
            :src="presentationInfo.thumbnail"
            class="img-fluid border-radius-lg"
            alt="thumbnail"
          />
        </a>
      </div>

      <div class="card-body pt-4">
        <span class="text-setting text-xs font-weight-bold my-2 dropdown"
          >PPT or PDF or IMAGE</span
        >

        <div class="row z-index: 5 dropdown">
          <a
            href="javascript:;"
            class="card-title h5 d-block text-darker col-8"
            @click="goToModifyPresentation()"
          >
            {{ presentationInfo.presentationName }}
          </a>
          <div
            class="col"
            type="button"
            id="dropdownMenuButton"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            @click="setName(presentationInfo.presentationName)"
          >
            <i class="ni ni-bold-down z-index: 5"></i>
          </div>
          <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <li>
              <a
                class="dropdown-item"
                href="#ModifyPPTNameModal"
                data-bs-toggle="modal"
                :name="presentationInfo.presentationName"
                :id="presentationInfo.presentationId"
                >이름 변경</a
              >
            </li>
            <li>
              <a
                class="dropdown-item"
                href="#DeletePPTModal"
                data-bs-toggle="modal"
                :id="presentationInfo.presentationId"
                >삭제</a
              >
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from '@/store';

export default {
  name: 'PresentationCardItem',
  props: { presentationInfo: Object },
  data() {
    return {};
  },
  computed: {},
  methods: {
    goToModifyPresentation() {
      this.$router.push({
        name: 'ModifyPresentation',
        params: {
          presentation_id: this.presentationInfo.presentationId,
          name: this.presentationInfo.presentationName,
        },
      });
    },
    async changeInfo(presentationInfo) {
      await store.dispatch(
        'mypage/setCurrentPresenatationInfo',
        presentationInfo,
      );
    },
    setName(title) {
      console.log('setName', title);
      this.$store.dispatch('mypage/setCurrentPresentationTitle', title);
    },
  },
};
</script>
<style scoped>
.card-presentation {
  width: 200px;
  height: 220px;
}
.card-title {
  text-align: center;
  font-size: 15px;
  font-weight: bold;
  white-space: nowrap; /* 한 줄 제한*/
  overflow: hidden; /* 넘어가는 글자 숨기기 */
  /* overflow: scroll; /* 넘어가는 글자 넘기기 */
  text-overflow: ellipsis; /* 말 줄임표 추가 */
}
.card-header {
  max-width: 180px;
  height: 100px;
}
.text-setting {
  color: #89abc6;
}
.ni:hover {
  color: rgb(139, 0, 116);
}
</style>
