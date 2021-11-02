<template>
  <div class="my-page-container col-12">
    <div id="houseScroll" class="container-box">
      <div v-for="(data, idx) in datas" v-bind:key="idx" class="box">
        <div class="inside-box">
          <img
            :src="data.thumbnail"
            class="card-img"
            style="width: 100%"
            alt="thumbnail"
          />
        </div>
        <div id="app" class="presentation-name-box" data-app>
          <span>{{ data.presentationName }}</span>
          <v-menu>
            <template v-slot:activator="{ on: menu, attrs }">
              <!-- hover -->
              <v-tooltip bottom>
                <template v-slot:activator="{ on: tooltip }">
                  <button
                    class="button-setting"
                    v-bind="attrs"
                    v-on="{ ...tooltip, ...menu }"
                  >
                    <i class="ni ni-bold-down"></i>
                  </button>
                </template>
                <span>설정을 변경하려면 클릭하세요.</span>
              </v-tooltip>
            </template>
            <!-- Menu bar -->
            <v-list>
              <v-list-item v-for="(item, index) in items" :key="index">
                <!-- 발표자료 수정 -->
                <div v-if="index == 0">
                  <router-link
                    :to="{
                      name: 'ModifyPresentation',
                      params: {
                        presentation_id: data.presentationId,
                        name: data.presentationName,
                      },
                    }"
                  >
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </router-link>
                </div>
                <!-- 이름 수정하기 -->
                <div v-else-if="index == 2">
                  <a
                    href="#ModifyPPTNameModal"
                    data-bs-toggle="modal"
                    @click="getCurrentPresenatationInfo(data)"
                    >{{ item.title }}</a
                  >
                </div>
                <!-- 발표자료 삭제하기 -->
                <div v-else-if="index == 3">
                  <a
                    href="#DeletePPTModal"
                    data-bs-toggle="modal"
                    @click="getCurrentPresenatationInfo(data)"
                    >{{ item.title }}</a
                  >
                </div>
                <!-- 나머지 Menu bar일 경우 -->
                <div v-else>
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                </div>
              </v-list-item>
            </v-list>
          </v-menu>
          <span></span>
        </div>
      </div>
    </div>
    <div class="bottom-button">
      <button
        class="btn btn-primary"
        data-bs-toggle="modal"
        data-bs-target="#AddPPTModal"
        style="margin: 0px"
      >
        발표자료 추가
      </button>
    </div>
    <AddPPTModal></AddPPTModal>
    <ModifyPPTNameModal></ModifyPPTNameModal>
    <DeletePPTModal></DeletePPTModal>
  </div>
</template>

<script>
import { getPresentations } from '@/api/presentation.js';
import AddPPTModal from './components/AddPPTModal.vue';
import ModifyPPTNameModal from './components/ModifyPPTNameModal.vue';
import DeletePPTModal from './components/DeletePPTModal.vue';
import { mapActions } from 'vuex';
import store from '@/store';

export default {
  name: 'Presentation',
  components: { AddPPTModal, ModifyPPTNameModal, DeletePPTModal },
  data: () => {
    return {
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
          userPresentationId: 0,
          presentationId: 0,
          thumbnail: 'none',
          presentationName: 'title1',
        },
        {
          userPresentationId: 1,
          presentationId: 1,
          thumbnail: 'none',
          presentationName: 'title3',
        },
        {
          userPresentationId: 0,
          presentationId: 0,
          thumbnail: 'none',
          presentationName: 'title1',
        },
        {
          userPresentationId: 1,
          presentationId: 1,
          thumbnail: 'none',
          presentationName: 'title3',
        },
        {
          userPresentationId: 0,
          presentationId: 0,
          thumbnail: 'none',
          presentationName: 'title1',
        },
        {
          userPresentationId: 1,
          presentationId: 1,
          thumbnail: 'none',
          presentationName: 'title3',
        },
        {
          userPresentationId: 0,
          presentationId: 0,
          thumbnail: 'none',
          presentationName: 'title1',
        },
        {
          userPresentationId: 1,
          presentationId: 1,
          thumbnail: 'none',
          presentationName: 'title3',
        },
        {
          userPresentationId: 0,
          presentationId: 0,
          thumbnail: 'none',
          presentationName: 'title1',
        },
        {
          userPresentationId: 1,
          presentationId: 1,
          thumbnail: 'none',
          presentationName: 'title3',
        },
      ],
      items: [
        { title: '발표자료 수정', link: '/' },
        { title: '공유하기' },
        { title: '이름 수정하기' },
        { title: '발표자료 삭제' },
      ],
    };
  },
  methods: {
    ...mapActions('mypage', ['getCurrentPresenatationInfo']),
    async getppt() {
      let userId = store.getters['users/getUser'].userId;
      try {
        this.datas = await getPresentations(userId);
      } catch (exp) {
        this.$alertify.error('프레젠테이션 갖고 오기에 실패했습니다.');
      }
    },
  },
  // created: {
  //   getppt(),
  // }
};
</script>

<style scoped>
#houseScroll {
  overflow: auto;
  margin: 10px 0 0 0;
}
.my-page-container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-end;
  width: 100%;
  height: 700px;
  border: 0px solid black;
  border-radius: 5px;
}
.container-box {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: flex-start;
  height: 90%;
  border: 2px solid black;
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
  margin: 0px;
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
