<template>
  <div class="col-12 my-page-container">
    <div id="houseScroll" class="container-box">
      <div class="card-presentation">
        <PresentationNewCardItem
          data-bs-toggle="modal"
          data-bs-target="#AddPPTModal"
        ></PresentationNewCardItem>
      </div>
      <div
        v-for="(data, idx) in datas"
        v-bind:key="idx"
        class="card-presentation"
      >
        <PresentationCardItem
          :presentationInfo="data"
          :key="idx"
        ></PresentationCardItem>
        <ModifyPPTNameModal :name="data.presentationName" :id="idx" />
      </div>
    </div>
    <AddPPTModal></AddPPTModal>
    <DeletePPTModal></DeletePPTModal>
  </div>
</template>

<script>
import { getPresentations } from '@/api/presentation.js';
import AddPPTModal from './components/AddPPTModal.vue';
import ModifyPPTNameModal from './components/ModifyPPTNameModal.vue';
import DeletePPTModal from './components/DeletePPTModal.vue';
import store from '@/store';
import PresentationCardItem from './components/PresentationCardItem.vue';
import PresentationNewCardItem from './components/PresentationNewCardItem.vue';
export default {
  name: 'Presentation',
  components: {
    AddPPTModal,
    ModifyPPTNameModal,
    DeletePPTModal,
    PresentationCardItem,
    PresentationNewCardItem,
  },
  data: () => {
    return {
      currentId: store.getters['mypage/getCurrentId'],
      currentTitle: store.getters['mypage/getCurrentTitle'],
      datas: [
        {
          userPresentationId: 0,
          presentationId: 0,
          thumbnail:
            'https://images.unsplash.com/photo-1517303650219-83c8b1788c4c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=bd4c162d27ea317ff8c67255e955e3c8&auto=format&fit=crop&w=2691&q=80',
          presentationName: 'title1',
        },
        {
          userPresentationId: 1,
          presentationId: 1,
          thumbnail: 'none',
          presentationName: 'title3',
        },
        {
          userPresentationId: 2,
          presentationId: 2,
          thumbnail:
            'https://images.unsplash.com/photo-1517303650219-83c8b1788c4c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=bd4c162d27ea317ff8c67255e955e3c8&auto=format&fit=crop&w=2691&q=80',
          presentationName: 'title4',
        },
      ],
    };
  },
  methods: {
    async getppt() {
      let userId = store.getters['users/getUser'].userId;
      try {
        this.datas = await getPresentations(userId);
      } catch (exp) {
        // console.log(exp);
        // console.log('에러');
        this.$toastError('프레젠테이션 갖고 오기에 실패했습니다.');
      }
    },
    async setPPTInfo(data) {
      await store.dispatch('mypage/setCurrentPresenatationInfo', data);
      console.log(store.state.mypage);
    },
  },
  created() {
    this.getppt();
  },
};
</script>

<style scoped>
#houseScroll {
  overflow: auto;
  margin: 10px 0 0 0;
}
.my-page-container {
  width: 100%;
  height: 700px;
  border: 0px solid rgb(235, 233, 233);
  border-radius: 5px;
}
.card-presentation {
  margin: 2%;
}
.container-box {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: flex-start;
  height: 90%;
  border: 2px solid rgb(235, 233, 233);
  border-radius: 5px;
}
.box {
  display: flex;
  flex-direction: column;
  text-align: center;
  align-items: center;
  width: 10vw;
  height: 10vw;
  min-width: 100px;
  min-height: 100px;
  max-width: 150px;
  max-height: 150px;
  border: 0px solid black;
  border-radius: 5px;
  margin: 10px;
  box-shadow: 2px 2px 2px 1px gray; /* x-position, y-position, blur, spread, color */
}
.inside-box {
  margin: 2px;
  width: 90%;
  height: 70%;
  border: 0px solid black;
  border-radius: 5px;
}
.presentation-name-box {
  display: flex;
  flex-direction: row;
  text-align: center;
}
.button-setting {
  display: flex;
  align-items: center;
}
.bottom-button {
  width: 100%;
  margin: 5px 0px;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
}

/* scroll bar*/
/* 스크롤바 설정*/
::-webkit-scrollbar {
  width: 5px;
}

/* 스크롤바 뒷 배경 설정*/
::-webkit-scrollbar-track {
  background-color: rgba(0, 0, 0, 0);
}

/* 스크롤바 막대 설정*/
::-webkit-scrollbar-thumb {
  height: 17%;
  background-color: #303f9f;
  /* 스크롤바 둥글게 설정    */
  border-radius: 10px;
}

/* 스크롤바 뒷 배경 설정*/
::-webkit-scrollbar-track {
  background-color: rgba(0, 0, 0, 0.33);
}
</style>
