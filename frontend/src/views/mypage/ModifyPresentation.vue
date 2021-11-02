<template>
  <div>
    <AppNav />
    <div id="modify-ppt">
      <div class="head-ppt">
        <div class="col-2">{{ $route.params.name }}</div>
        <div class="head-ppt-center">
          <div>효과 미리보기</div>
          <div class="col-10 head-animation">
            <div
              v-for="effect in effects"
              :class="['effect-container']"
              :key="effect"
              :id="effect"
              @click="showExample(effect)"
            >
              <div>
                <img
                  :src="slides[idx].formdata"
                  style="max-width: 100px; max-height: 100px; margin-right: 5px"
                  alt="animations"
                />
                <div class="text-center">
                  {{ effect }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="body-ppt">
        <div class="col-3 ppt-overflow">
          <div
            class="choose-ppt"
            v-for="(slide, idx) in slides"
            :key="idx"
            @click="setIdx(slide.sequenceNum)"
          >
            <img
              :src="slides[idx].formdata"
              style="max-width: 20vw; height: 70px"
              alt="thumbnail"
            />
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
            <textarea name="text" id="" cols="30" rows="10"></textarea>
          </div>
          <div class="buttons-setting">
            <div>
              <button @click="goBack()">목록 보기</button>
            </div>
            <div>
              <button class="buttons-detail" @click="showAllPPT()">
                전체 미리보기
              </button>
              <button class="buttons-detail" @click="savePPT()">
                슬라이드 저장
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AppNav from '@/components/common/AppNav.vue';

export default {
  name: 'ModifyPresentation',
  data() {
    return {
      idx: 0,
      effects: [
        'basic',
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
            'http://designbase.co.kr/wp-content/uploads/2020/12/webcoding-06-overview.jpg',
          sequenceNum: 1,
          script: 'ppt1',
        },
        {
          formdata:
            'https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png',
          sequenceNum: 2,
          script: 'ppt2',
        },
        {
          formdata: 'https://d2gd6pc034wcta.cloudfront.net/images/logo@2x.png',
          sequenceNum: 3,
          script: 'ppt3',
        },
        {
          formdata:
            'https://upload.acmicpc.net/54146fb3-bcf1-4f78-9caa-8e2c6440d7aa/',
          sequenceNum: 4,
          script: 'ppt4',
        },
        {
          formdata:
            'https://papago.naver.com/97ec80a681e94540414daf2fb855ba3b.svg',
          sequenceNum: 5,
          script: 'ppt5',
        },
        {
          formdata: 'https://edu.ssafy.com/asset/images/header-logo.jpg',
          sequenceNum: 6,
          script: 'ppt6',
        },
        {
          formdata:
            'https://www.samsung.com/sec/static/_images/common/logo_samsung_black.svg',
          sequenceNum: 7,
          script: 'ppt7',
        },
        {
          formdata:
            'https://image7.coupangcdn.com/image/coupang/common/logo_coupang_w350.png',
          sequenceNum: 8,
          script: 'ppt8',
        },
        {
          formdata: 'data9',
          sequenceNum: 9,
          script: 'ppt9',
        },
        {
          formdata: 'data10',
          sequenceNum: 10,
          script: 'ppt10',
        },
        {
          formdata: 'data11',
          sequenceNum: 11,
          script: 'ppt11',
        },
        {
          formdata: 'data12',
          sequenceNum: 12,
          script: 'ppt12',
        },
        {
          formdata: 'data13',
          sequenceNum: 13,
          script: 'ppt13',
        },
        {
          formdata: 'data14',
          sequenceNum: 14,
          script: 'ppt14',
        },
        {
          formdata: 'data15',
          sequenceNum: 15,
          script: 'ppt15',
        },
        {
          formdata: 'data16',
          sequenceNum: 16,
          script: 'ppt16',
        },
        {
          formdata: 'data17',
          sequenceNum: 17,
          script: 'ppt17',
        },
        {
          formdata: 'data18',
          sequenceNum: 18,
          script: 'ppt18',
        },
        {
          formdata: 'data19',
          sequenceNum: 19,
          script: 'ppt19',
        },
        {
          formdata: 'data20',
          sequenceNum: 20,
          script: 'ppt20',
        },
        {
          formdata: 'data21',
          sequenceNum: 21,
          script: 'ppt21',
        },
        {
          formdata: 'data22',
          sequenceNum: 22,
          script: 'ppt22',
        },
        {
          formdata: 'data23',
          sequenceNum: 23,
          script: 'ppt23',
        },
        {
          formdata: 'data24',
          sequenceNum: 24,
          script: 'ppt24',
        },
        {
          formdata: 'data25',
          sequenceNum: 25,
          script: 'ppt25',
        },
        {
          formdata: 'data26',
          sequenceNum: 26,
          script: 'ppt26',
        },
        {
          formdata: 'data27',
          sequenceNum: 27,
          script: 'ppt27',
        },
        {
          formdata: 'data28',
          sequenceNum: 28,
          script: 'ppt28',
        },
        {
          formdata: 'data29',
          sequenceNum: 29,
          script: 'ppt29',
        },
      ],
    };
  },
  components: { AppNav },
  computed: {
    messageData() {
      const data = {
        id: 'changePresentation',
        currentPage: this.$store.state.mypage.currentPage,
        location: this.$store.state.mypage.location,
        size: this.$store.state.mypage.size,
      };
      return data;
    },
    currentEffect() {
      return this.$store.state.mypage.transition;
    },
  },
  methods: {
    goBack: function () {
      this.$router.push({ name: 'Presentation' });
    },
    showExample(effect) {
      const el = document.getElementById(effect);
      el.classList.add(effect);
      setTimeout(function () {
        el.classList.remove(effect);
      }, 1000);
      // this.selectEffect(effect);
    },
    // selectEffect(effect) {
    //   const message = {
    //     ...this.messageData,
    //     transition: effect,
    //   };
    //   this.$store.dispatch('mypage/sendMessage', message);
    // },
    showAllPPT() {
      console.log('showAllPPT');
    },
    savePPT() {
      console.log('savePPT');
    },
    setIdx(num) {
      this.idx = num - 1;
    },
  },
};
</script>

<style scoped>
#modify-ppt {
  margin: 1vw;
}
.head-ppt {
  display: flex;
  flex-direction: row;
}
.choose-ppt {
  margin: 10px 0px;
}
.choose-ppt:hover {
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
.body-ppt {
  display: flex;
  flex-direction: row;
}
.ppt-overflow {
  margin-top: 2vh;
  height: 60vh;
  text-align: center;
  overflow: auto;
}
.body-margin-top {
  margin-top: 2vh;
}
.body-main {
  flex-direction: column;
  border: 2px solid black;
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
.basic {
  animation: basic 0.7s;
}
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
