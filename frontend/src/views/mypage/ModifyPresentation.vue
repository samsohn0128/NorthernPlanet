<template>
  <div>
    <AppNav />
    <nav
      class="
        navbar navbar-expand-lg
        blur blur-rounded
        top-0
        border-bottom
        z-index-3
        shadow
        w-100
        mt-1
        d-none d-lg-block
        my-1
        py-1
      "
    >
      <div class="container-fluid">
        <a
          class="navbar-brand font-weight-bolder ms-3"
          rel="tooltip"
          data-placement="bottom"
          target="_blank"
        >
          {{ $route.params.name }}
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navigation"
          aria-controls="navigation"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navigation">
          <ul class="navbar-nav navbar-nav-hover mx-auto">
            <li class="nav-item px-3" @click="savePPT(idx)">
              <a class="nav-link"> Save all</a>
            </li>

            <li class="nav-item px-3" @click="goBackPresentation()">
              <a class="nav-link"> Back to the list </a>
            </li>

            <li class="nav-item px-3" @click="showAllPPT()">
              <a class="nav-link"> Preview </a>
            </li>
          </ul>

          <ul class="navbar-nav">
            <button
              class="btn btn-sm bg-gradient-dark btn-round mb-0 me-1"
              @click="animationTabToggle('hi')"
            >
              Animation
            </button>
          </ul>
        </div>
      </div>
    </nav>
    <!-- End Navbar -->
    <div id="modify-ppt">
      <div class="head-ppt">
        <div class="container-fluid">
          <div class="head-animation" v-if="showAnimation == true">
            <div
              v-for="effect in effects"
              :class="['effect-container']"
              :key="effect"
              :id="effect"
              @click="showExample(effect)"
            >
              <h6>{{ effect }}</h6>
            </div>
          </div>
        </div>
      </div>
      <div class="body-ppt">
        <div class="col-3 ppt-overflow">
          <div
            class="choose-ppt card"
            v-for="(slide, idx) in slides"
            :key="idx"
            @click="setIdx(slide.sequenceNum)"
          >
            <div class="choose-ppt-icons">
              <i class="ni ni-fat-add"></i><i class="ni ni-fat-remove"></i>
            </div>
            <div class="PPTbox">
              <div style="width: 100%; height: 150px">
                <img
                  :src="slides[idx].formdata"
                  style="max-width: 20vw; max-height: 9vw"
                  alt="thumbnail"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="col-9 body-margin-top">
          <div class="body-main">
            <div>
              <img
                :src="slides[idx].formdata"
                style="max-width: 60vw; max-height: 50vh; margin: 5px"
                alt="animations"
              />
            </div>
          </div>
          <div>
            <Editor
              ref="toastuiEditor"
              :options="editorOptions"
              height="300px"
              initialEditType="wysiwyg"
              previewStyle="vertical"
              @blur="saveEditorText"
            />
          </div>
        </div>
      </div>
    </div>
    <AddPPTPictureModal></AddPPTPictureModal>
  </div>
</template>

<script>
import AppNav from '@/components/common/AppNav.vue';
import AddPPTPictureModal from './components/AddPPTPictureModal.vue';
import {
  // getPresentationDetail,
  presentationAddDelete,
  savePresentation,
} from '@/api/presentation.js';
import { mapActions } from 'vuex';
import { getSlide, updateScript } from '@/api/slide.js';
import { Editor } from '@toast-ui/vue-editor';
import store from '@/store';

export default {
  name: 'ModifyPresentation',
  components: { AppNav, AddPPTPictureModal, Editor },
  data() {
    return {
      idx: 0,
      presentationId: this.$route.params.presentation_id,
      effects: [
        'fadein',
        'fadedown',
        'fadeleft',
        'faderight',
        'fadeup',
        'backdown',
        'backup',
        'flipx',
        'flipy',
        'rotatein',
      ],
      slides: [
        {
          formdata:
            'https://import.cdn.thinkific.com/292401/PuGMXOphTKWoVdN3FOd6_D__6___1__png',
          sequenceNum: 0,
          script: 'ppt1',
          effect: 0,
        },
        {
          formdata:
            'http://designbase.co.kr/wp-content/uploads/2020/12/webcoding-06-overview.jpg',
          sequenceNum: 1,
          script: 'ppt1',
          effect: 0,
        },
        {
          formdata:
            'https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png',
          sequenceNum: 2,
          script: 'ppt2',
          effect: 1,
        },
        {
          formdata: 'https://d2gd6pc034wcta.cloudfront.net/images/logo@2x.png',
          sequenceNum: 3,
          script: 'ppt3',
          effect: 1,
        },
        {
          formdata:
            'https://upload.acmicpc.net/54146fb3-bcf1-4f78-9caa-8e2c6440d7aa/',
          sequenceNum: 4,
          script: 'ppt4',
          effect: 0,
        },
        {
          formdata:
            'https://papago.naver.com/97ec80a681e94540414daf2fb855ba3b.svg',
          sequenceNum: 5,
          script: 'ppt5',
          effect: 0,
        },
        {
          formdata: 'https://edu.ssafy.com/asset/images/header-logo.jpg',
          sequenceNum: 6,
          script: 'ppt6',
          effect: 0,
        },
        {
          formdata:
            'https://www.samsung.com/sec/static/_images/common/logo_samsung_black.svg',
          sequenceNum: 7,
          script: 'ppt7',
          effect: 0,
        },
        {
          formdata:
            'https://image7.coupangcdn.com/image/coupang/common/logo_coupang_w350.png',
          sequenceNum: 8,
          script: 'ppt8',
          effect: 0,
        },
        {
          formdata: 'data9',
          sequenceNum: 9,
          script: 'ppt9',
          effect: 0,
        },
        {
          formdata: 'data10',
          sequenceNum: 10,
          script: 'ppt10',
          effect: 0,
        },
      ],
      items: [{ title: 'PPT 추가' }, { title: 'PPT 삭제' }],
      editorOptions: {
        minHeight: '200px',
        hideModeSwitch: true,
        toolbarItems: [
          ['heading', 'bold', 'italic', 'strike'],
          ['hr', 'quote'],
          ['ul', 'ol', 'task', 'indent', 'outdent'],
          ['scrollSync'],
        ],
      },
      slideId: 1, // 슬라이드 번호를 가져올 수 있게 되면 변경 예정 (임시로 넣어둔 값)
      editorText: '',
      showAnimation: false,
    };
  },
  computed: {
    messageData() {
      const data = {
        id: 'changePresentation',
        currentPage: store.state.mypage.currentPage,
        location: store.state.mypage.location,
        size: store.state.mypage.size,
      };
      return data;
    },
    currentEffect() {
      return store.state.mypage.transition;
    },
  },
  methods: {
    ...mapActions('mypage', ['setSequenceNum']),
    // 백엔드 연결 뒤에 주석 해제, 아래 mounted도!
    // getPresentationData() {
    //   getPresentationDetail(this.presentationId);
    //   // this.slides = getPresentationDetail(this.presentationId);
    // },
    goBackPresentation() {
      this.$router.push({ name: 'Presentation' });
    },
    showExample(effect) {
      const el = document.getElementById(effect);
      el.classList.add(effect);
      setTimeout(function () {
        el.classList.remove(effect);
      }, 1000);
      this.selectEffect(effect);
    },
    selectEffect(effect) {
      store.dispatch('mypage/setEffect', effect);
    },
    showAllPPT() {
      console.log('showAllPPT');
    },
    // 사진을 등록한다.
    selectPicture() {},
    // 사진 먼저 등록받고나서 여기로 이동
    setPicture(sequenceNum) {
      let userId = store.getters['users/getUser'];
      let slideId = this.presentationId;
      let data = {
        userId,
        slides: [
          {
            slideId, //(추가했을 경우 -1)
            'multipart/form-data': 'picture', // 사진 등록 후 변경
            sequenceNum, //(삭제했을 경우 -1)
            // effect,
          },
        ],
      };
      savePresentation(data);
    },
    // 슬라이드를 저장한다.
    savePPT(sequenceNum) {
      let userId = store.getters['users/getUser'];
      let slideId = this.presentationId;
      // let effect = store.getters['mypage/getEffect']; // effect DB, API에 등록 후 사용하기
      let data = {
        userId,
        slides: [
          {
            slideId, //(추가했을 경우 -1)
            sequenceNum, //(삭제했을 경우 -1)
            // effect,
          },
        ],
      };
      savePresentation(data);
    },
    setIdx(num) {
      this.idx = num;
    },
    // PPT를 새로 추가한다.
    addPPT(sequenceNum) {
      let presentationId = this.presentationId;
      let userId = store.getters['users/getUser'];
      let data = {
        userId,
        slides: [
          {
            slideId: -1, //(추가했을 경우 -1)
            sequenceNum, //(삭제했을 경우 -1)
          },
        ],
      };
      presentationAddDelete(presentationId, data);
    },
    // PPT를 제거한다.
    deletePPT(slideId) {
      let presentationId = this.presentationId;
      let userId = store.getters['users/getUser'];
      let data = {
        userId,
        slides: [
          {
            slideId, //(추가했을 경우 -1)
            sequenceNum: -1, //(삭제했을 경우 -1)
          },
        ],
      };
      presentationAddDelete(presentationId, data);
    },
    saveEditorText() {
      let curEditorText = this.$refs.toastuiEditor.invoke('getHTML');
      if (this.editorText === curEditorText) return;

      let updateScriptReq = {
        slideId: 1,
        script: this.$refs.toastuiEditor.invoke('getHTML'),
      };

      updateScript(updateScriptReq).then(res => {
        if (res.status != 200) {
          this.$toastError(
            '대본을 저장하던 중에 오류가 발생했습니다. 대본이 유실될 수 있습니다.',
          );
          return;
        } else {
          this.$toastSuccess('대본이 수정되었습니다.');

          this.editorText = curEditorText;
        }
      });
    },
    animationTabToggle(message) {
      console.log(message);
      this.showAnimation = !this.showAnimation;
      console.log('show animation: ', this.showAnimation);
    },
  },
  created() {
    getSlide(this.slideId).then(res => {
      if (res.status != 200) {
        this.$toastError('슬라이드 정보를 가져오는 중에 오류가 발생했습니다.');
        return;
      } else {
        this.editorText = res.data.script;
        this.$refs.toastuiEditor.invoke('setHTML', res.data.script);
      }
    });
  },
  // 백엔드 연결 뒤에 주석 해제
  // mounted() {
  //   this.getPresentationData();
  // },
};
</script>

<style scoped>
#modify-ppt {
  margin-top: 0%;
  margin-left: 2%;
  margin-right: 2%;
  padding: 0%;
}
.effect-container {
  position: relative;
  cursor: pointer;
  margin: 1%;
  width: 200px;
  height: 80px;
  background: white;
  border-radius: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.head-ppt {
  display: flex;
  flex-direction: row;
  border-radius: 15px;
  background-color: aliceblue;
}
.title-css {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 50px;
  font-weight: bold;
  color: black;
}
.choose-ppt {
  margin: 10px 0px;
}
.choose-ppt:hover {
  cursor: pointer;
}

.PPTbox {
  width: 100%;

  /* flex-direction: row; */
  /* justify-content: space-between; */
  /* align-items: top; */
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.25);
}

.head-ppt-center {
  flex-direction: column;
}
.head-animation {
  display: flex;
  flex-direction: row;
  overflow: auto;
}
.body-ppt {
  display: flex;
  flex-direction: row;
}
.ppt-overflow {
  margin-top: 2vh;
  height: 80vh;
  text-align: center;
  overflow: auto;
  border-right-color: rgb(85, 85, 85);
  border-width: thick;
}
.body-margin-top {
  margin-top: 2vh;
}
.effect-mark-box {
  display: flex;
  align-items: center;
  width: 10px;
  height: 10px;
  background: rgb(110, 185, 255);
}
.body-main {
  flex-direction: column;
  text-align: center;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.25);
  resize: vertical;
  overflow: auto;
}
.buttons-setting {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.buttons-detail {
  margin: 0px 1vw;
}

/* 애니메이션 설정 */

.fadein {
  animation: fadeIn 0.7s;
}
.fadedown {
  animation: fadeInDown 0.7s;
}
.fadeleft {
  animation: fadeInLeft 0.7s;
}
.faderight {
  animation: fadeInRight 0.7s;
}
.fadeup {
  animation: fadeInUp 0.7s;
}
.backdown {
  animation: backInDown 0.7s;
}
.backup {
  animation: backInUp 0.7s;
}
.flipx {
  animation: flipInX 0.7s;
}
.flipy {
  animation: flipInY 0.7s;
}
.rotatein {
  animation: rotateIn 0.7s;
}

/* scroll bar*/
/* 스크롤바 설정*/
::-webkit-scrollbar {
  width: 5px;
  height: 5px;
}

/* 스크롤바 뒷 배경 설정*/
::-webkit-scrollbar-track {
  background-color: aliceblue;
}

/* 스크롤바 막대 설정*/
::-webkit-scrollbar-thumb {
  height: 17%;
  background-color: rgb(228, 226, 226);
  /* 스크롤바 둥글게 설정    */
  border-radius: 10px;
}
</style>
