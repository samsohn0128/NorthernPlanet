<template>
  <div class="my-page-container col-12">
    <div id="houseScroll" class="container-box">
      <div v-for="(data, idx) in datas" v-bind:key="idx" class="box">
        <div class="inside-box">
          <img
            :src="data.thumbnail"
            class="card-img"
            style="width: 90px; height: 60px"
            alt="thumbnail"
          />
        </div>
        <div class="text-box">
          <span>
            <div id="app" class="text-center" data-app>
              <v-menu>
                <template v-slot:activator="{ on: menu, attrs }">
                  <!-- hover -->
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on: tooltip }">
                      <v-btn
                        color="primary"
                        dark
                        v-bind="attrs"
                        v-on="{ ...tooltip, ...menu }"
                      >
                        {{ data.presentationName }}
                      </v-btn>
                    </template>
                    <span>설정을 변경하려면 클릭하세요.</span>
                  </v-tooltip>
                </template>
                <!-- Menu bar -->
                <v-list>
                  <v-list-item v-for="(item, index) in items" :key="index">
                    <!-- 첫 번째 Menu bar일 경우 -->
                    <div v-if="index == 0">
                      <router-link
                        :to="{
                          name: 'ModifyPPT',
                          params: {
                            id: data.presentationId,
                            name: data.presentationName,
                          },
                        }"
                      >
                        <v-list-item-title>{{ item.title }}</v-list-item-title>
                      </router-link>
                    </div>
                    <!-- 나머지 Menu bar일 경우 -->
                    <div v-else>
                      <v-list-item-title>{{ item.title }}</v-list-item-title>
                    </div>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </span>
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
  </div>
</template>

<script>
// import { getpresentations } from '@/api/presentation.js';
import AddPPTModal from './components/AddPPTModal.vue';

export default {
  name: 'MyPPT',
  components: { AddPPTModal },
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
    // async getppt() {
    //    let userData = {
    //    user_id: this.$store.state.users.login.userid,
    //    name: this.username,
    //    };
    //   try {
    //     const response = await getpresentations(userData);
    //     this.datas = response.data;
    //   } catch (exp) {
    //     this.$alertify.error('프레젠테이션을 갖고 오기에 실패했습니다.');
    //   }
    // },
  },
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
  height: 90%;
  border: 2px solid black;
  border-radius: 5px;
}
.box {
  display: flex;
  flex-direction: column;
  text-align: center;
  align-items: center;
  width: 100px;
  height: 100px;
  border: 0px solid black;
  border-radius: 5px;
  margin: 10px;
}
.inside-box {
  margin: 2px;
  width: 90%;
  height: 70%;
  border: 0px solid black;
  border-radius: 5px;
}
.text-box {
  width: 100%;
  padding-left: 8px; /* 우측 버튼 크기에 맞게 수정하기 */
  display: flex;
  flex-direction: row;
  justify-content: space-between;
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
