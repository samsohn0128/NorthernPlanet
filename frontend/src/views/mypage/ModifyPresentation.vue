<template>
  <div>
    <AppNav />
    <div class="nav-setting">
      <div class="nav-detail-left-setting">
        <button
          class="button-setting btn btn-sm btn-round mb-0 me-1"
          @click="goBackPresentation()"
        >
          Back to the list
        </button>
      </div>
      <div class="nav-detail-setting">
        {{ $route.params.name }}
      </div>
      <div class="nav-detail-right-setting">
        <button
          class="button-setting btn btn-sm btn-round mb-0 me-1"
          @click="showAllPPT()"
        >
          Preview
        </button>
        <button
          class="button-setting btn btn-sm btn-round mb-0 me-1"
          @click="animationTabToggle('hi')"
        >
          Select Animation
        </button>
        <button
          class="button-setting btn btn-sm btn-round mb-0 me-1"
          @click="savePPT()"
        >
          Save all
        </button>
      </div>
    </div>
    <!-- End Navbar -->
    <div id="modify-ppt">
      <div class="head-ppt">
        <div class="container-fluid">
          <div class="head-animation" v-if="showAnimation == true">
            <div
              v-for="(effect, name) in effects"
              :class="[
                { 'container-border': effect === slideList[idx].effect },
                'effect-container',
              ]"
              :key="effect"
              :id="effect"
              @click="selectEffect(effect)"
              @mouseenter="showExample(effect, name)"
            >
              <h6>{{ name }}</h6>
            </div>
          </div>
        </div>
      </div>
      <div class="body-ppt">
        <div class="col-3 ppt-overflow">
          <!-- 자동 업데이트 싫으면 @end="updateItemOrder 없애기-->
          <!-- <draggable v-model="slideList" @end="updateItemOrder"> -->
          <draggable v-model="slideList">
            <transition-group tag="div" class="choose-ppt card">
              <div
                class="choose-ppt card"
                v-for="(slide, idx) in slideList"
                :key="`key-${slide.slideId}`"
                @click="setIdx(idx)"
              >
                <div class="choose-ppt-icons">
                  <i
                    class="ni ni-fat-remove"
                    @click="deleteTheSlide(slide.slideId)"
                  ></i>
                  <i
                    v-if="slide.effect != null && slide.effect != 0"
                    class="ni ni-spaceship"
                  ></i>
                </div>
                <div class="PPTbox">
                  <div style="width: 100%; height: 150px">
                    <img
                      :src="slide.slideFile"
                      style="max-width: 100%; max-height: 150px"
                      alt="thumbnail"
                    />
                  </div>
                </div>
              </div>
            </transition-group>
          </draggable>
          <div class="choose-ppt card">
            <div
              class="PPTbox-select"
              style="
                width: 100%;
                height: 150px;
                display: flex;
                justify-content: center;
              "
            >
              <button
                class="
                  button-setting
                  PPTbox-select-detail
                  btn btn-sm btn-round
                  mb-0
                  me-1
                "
                type="button"
                data-bs-toggle="modal"
                data-bs-target="#AddSlideModal"
              >
                Click to Add new slide
              </button>
              <img
                class="image thumbnail-setting"
                :src="imgUrl.first"
                alt=""
                @click="dialogVisible.first = true"
              />
            </div>
          </div>
        </div>
        <div class="col-9 body-margin-top">
          <div class="body-main">
            <div>
              <img
                :src="slideList[idx].slideFile"
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
          <!-- <img id="ItemPreview" src="" /> -->
        </div>
      </div>
    </div>
    <AddSlideModal></AddSlideModal>
  </div>
</template>

<script>
import draggable from 'vuedraggable';
import AppNav from '@/components/common/AppNav.vue';
import AddSlideModal from './components/AddSlideModal.vue';
import {
  getPresentationDetail,
  savePresentation,
  addSlide,
  deleteSlide,
} from '@/api/presentation.js';
import { mapActions } from 'vuex';
import { updateScript } from '@/api/slide.js';
import { Editor } from '@toast-ui/vue-editor';
import store from '@/store';

export default {
  name: 'ModifyPresentation',
  components: { draggable, AppNav, AddSlideModal, Editor },
  data() {
    return {
      idx: 0,
      presentationId: this.$route.params.presentation_id,
      userId: store.state.users.user.userId,
      debounce: false,
      effects: {
        default: 0,
        fadein: 1,
        fadedown: 2,
        fadeleft: 3,
        faderight: 4,
        fadeup: 5,
        backdown: 6,
        backup: 7,
        flipx: 8,
        flipy: 9,
        rotatein: 10,
      },
      presentationName: null,
      size: null,
      uploadTime: null,
      slideList: [
        {
          effect: null,
          script: null,
          sequence: null,
          slideFile: null,
          slideId: null,
        },
      ],
      sendSlideList: [],
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
      dialogVisible: { first: false },
      imgUrl: { first: '' },
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
  },
  methods: {
    ...mapActions('mypage', ['setSequenceNum']),
    // DB에서 Presentation 안의 Slide들을 가져온다.
    // 백엔드 연결 뒤에 주석 해제, 아래 mounted도!
    async getPresentationData() {
      let response = await getPresentationDetail(
        this.userId,
        this.presentationId,
      );
      // ByteArray를 img로 변경
      let imgByteArray = response.data.slideList;
      imgByteArray.forEach(element => {
        element.slideFile = 'data:image/png;base64,' + element.slideFile;
      });
      // slideList 대입
      this.slideList = response.data.slideList;
      console.log('시작 slideList: ', this.slideList);
      // 시작대본 설정
      if (this.slideList[0].slideId === null) return;
      this.editorText = this.slideList[0].script;
      this.$refs.toastuiEditor.invoke('setHTML', this.editorText);
    },
    // Presentation으로 돌아간다.
    goBackPresentation() {
      this.$router.push({ name: 'Presentation' });
    },
    // Effect의 예시를 보여준다.
    showExample(effect, name) {
      console.log(effect, name);
      const el = document.getElementById(effect);
      console.log(el);
      el.classList.add(name);
      setTimeout(function () {
        el.classList.remove(name);
      }, 1000);
    },
    // 고른 Effect의 설정을 저장한다.
    selectEffect(effect) {
      let num = this.idx;
      this.slideList[num].effect = effect;
    },
    // 전체 보기 화면으로 이동
    showAllPPT() {
      this.$router.push({
        name: 'PresentationPreview',
        params: {
          userId: this.userId,
          presentationId: this.presentationId,
        },
      });
    },
    // 슬라이드를 저장한다.
    async savePPT() {
      try {
        await this.updateItemOrder(); // Drag&Drop으로 옮긴 순서 정보 저장하기
        let presentationId = this.presentationId;
        let userId = store.getters['users/getUserId'];
        // console.log('presentationId: ', presentationId);
        // console.log('userId: ', userId);
        let data = {
          userId,
          presentationId,
          slides: this.sendSlideList,
        };
        console.log('끝 data: ', data);
        await savePresentation(data);
        await this.$toastSuccess('슬라이드를 저장하였습니다.');
        this.$router.go();
      } catch (exp) {
        console.log(exp);
        this.$toastError('슬라이드 저장에 실패했습니다.');
      }
    },
    // 현재 선택한 Idx 설정하기 (Effect 저장시에 필요)
    setIdx(num) {
      this.idx = num;

      console.log('before getslide slidelist: ', this.slideList);
      // getSlide(this.slideList[this.idx].slideId).then(res => {
      //   if (res.status != 200) {
      //     this.$toastError(
      //       '슬라이드 정보를 가져오는 중에 오류가 발생했습니다.',
      //     );
      //     return;
      //   } else {
      //     this.editorText = res.data.script;
      //     this.$refs.toastuiEditor.invoke('setHTML', res.data.script);
      //   }
      // });
      this.editorText = this.slideList[this.idx].script
        ? this.slideList[this.idx].script
        : '';
      this.$refs.toastuiEditor.invoke('setHTML', this.editorText);
    },
    // PPT를 새로 추가한다.
    addnewSlide() {
      // Modal 추가하기

      let userId = this.userId;
      let presentationId = this.presentationId;
      let data = {
        userId,
        presentationId,
      };
      addSlide(data);
    },
    // PPT를 제거한다.
    deleteTheSlide(slideId) {
      try {
        deleteSlide(slideId);
        this.$toastSuccess('슬라이드가 삭제되었습니다.');
        this.$router.go();
      } catch (exp) {
        this.$toastError('사진 삭제에 실패했습니다.');
      }
    },
    updateItemOrder: function () {
      // get your info then...
      let items = this.slideList.map(function (slide, index) {
        return {
          script: slide.script,
          sequence: index,
          slideId: slide.slideId,
          effect: slide.effect,
        };
      });
      console.log(items);
      this.sendSlideList = items;
      if (this.debounce) return;
      this.debounce = function (items) {
        this.debounce = false;
        // send it to your db
        console.log(items);
      }.bind(this, items);
    },
    saveEditorText() {
      let curEditorText = this.$refs.toastuiEditor.invoke('getHTML');
      if (this.editorText === curEditorText) return;

      let updateScriptReq = {
        slideId: this.slideList[this.idx].slideId,
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
          this.slideList[this.idx].script = curEditorText;
        }
      });
    },
    animationTabToggle(message) {
      console.log(message);
      this.showAnimation = !this.showAnimation;
      console.log('show animation: ', this.showAnimation);
    },
  },
  created() {},
  // 백엔드 연결 뒤에 주석 해제
  mounted() {
    this.getPresentationData();
  },
};
</script>

<style scoped>
#modify-ppt {
  margin-top: 0%;
  margin-left: 2%;
  margin-right: 2%;
  padding: 0%;
}
.nav-setting {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  background: #f9fbe7;
  padding: 5px 0px;
}
.nav-detail-left-setting {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
  width: 30vw;
  padding-left: 10px;
}
.nav-detail-setting {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  text-align: center;
  width: 20vw;
  font-weight: bold;
  color: black;
}
.nav-detail-right-setting {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  text-align: center;
  width: 30vw;
}
.button-setting {
  background: #4ba3c7;
  color: white;
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
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  /* flex-direction: row; */
  /* justify-content: space-between; */
  /* align-items: top; */
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.25);
}
.PPTbox-select {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  /* flex-direction: row; */
  /* justify-content: space-between; */
  /* align-items: top; */
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.25);
}
.PPTbox-select:hover {
  cursor: default;
}
.PPTbox-select-detail:hover {
  cursor: pointer;
}

.head-ppt-center {
  flex-direction: column;
}
.head-animation {
  display: flex;
  flex-direction: row;
  overflow: auto;
}
.container-border {
  border: 0.4rem solid;
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
.thumbnail-setting {
  max-width: 200px;
  max-height: 120px;
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
