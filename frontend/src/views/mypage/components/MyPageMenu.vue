<template>
  <div class="main-square">
    <!-- <ul class="list-group">
      <li class="list-group-item square">
        <router-link to="/mypage" class="router">내 정보</router-link>
      </li>
      <li class="list-group-item square">
        <router-link to="/mypage/presentation" class="router"
          >내 발표자료</router-link
        >
      </li>
      <li class="list-group-item square">공유된 발표자료</li> 
    </ul> -->

    <div class="nav-wrapper position-relative end-0">
      <ul class="nav nav-pills nav-fill flex-column p-1" role="tablist">
        <li class="nav-item">
          <a
            class="button-setting nav-link mb-0 px-0 py-1"
            data-bs-toggle="tab"
            role="tab"
            aria-controls="preview"
            aria-selected="true"
            @click="navClick(1)"
          >
            My Profile
          </a>
        </li>
        <li class="nav-item">
          <a
            class="button-setting nav-link mb-0 px-0 py-1"
            data-bs-toggle="tab"
            role="tab"
            aria-controls="code"
            aria-selected="false"
            @click="navClick(2)"
          >
            Presentation
          </a>
        </li>
        <div
          class="moving-tab position-absolute nav-link"
          style="padding: 0px; transition: all 0.5s ease 0s; width: 190px"
        >
          <a
            class="nav-link mb-0 px-0 py-1 active"
            data-bs-toggle="tab"
            href="#dashboard-tabs-vertical"
            role="tab"
            aria-controls="code"
            aria-selected="true"
          >
            My Profile</a
          >
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
import router from '@/router';
export default {
  name: 'MyPageMenu',
  data() {
    return {
      nowIndex: 1,
    };
  },
  mounted() {
    const link = document.location.href;
    var linkArray = link.split('/');
    if (linkArray[linkArray.length - 1] == 'presentation') {
      document.getElementsByClassName('moving-tab')[0].style.transform =
        'translate3d(0px, 34px, 0px)';
      this.nowIndex = 2;
    }
  },
  methods: {
    navClick(index) {
      const movingTab = document.getElementsByClassName('moving-tab')[0];
      if (index == 1 && this.nowIndex != index) {
        router.push({ name: 'MyInfo' });
        movingTab.style.transform = 'translate3d(0px, 0px, 0px)';
        this.nowIndex = index;
      } else if (index == 2 && this.nowIndex != index) {
        router.push({ name: 'Presentation' });
        movingTab.style.transform = 'translate3d(0px, 34px, 0px)';
        this.nowIndex = index;
      }
    },
  },
};
</script>

<style scoped>
.main-square {
  width: 200px;
  height: 80vh;
  text-align: center;
  border-style: solid;
  border-width: 1.5px;
  border-color: rgb(235, 233, 233);
  border-radius: 10px;
  margin: 12px;
}
.square {
  padding: 5px 0px;
  text-align: center;
  border-style: solid;
  border-width: 1.5px;
  border-color: black;
}
.square:hover {
  background: beige;
  font-weight: bold;
}
.router:hover {
  color: darkblue;
}
.button-setting:hover {
  cursor: pointer;
}
</style>
