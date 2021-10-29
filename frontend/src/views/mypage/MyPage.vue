<template>
  <div>
    <AppNav />
    <div class="my-page col-12">
      <span><MyPageMenu /></span>
      <span class="width-setting"><router-view></router-view></span>
    </div>
    <AppFooter />
  </div>
</template>
<script>
import AppNav from '@/components/common/AppNav.vue';
import AppFooter from '@/components/common/AppFooter.vue';
import MyPageMenu from './components/MyPageMenu.vue';
export default {
  name: 'MyPage',
  components: { AppNav, AppFooter, MyPageMenu },
};
</script>

<style scoped>
.my-page {
  display: flex;
  flex-direction: row;
  /* background: url('https://ak4.picdn.net/shutterstock/videos/13222694/thumb/3.jpg?ip=x480');
  animation: animate 5s linear infinite; */
}
@keyframes animate {
  from {
    background-position: 0% 50%;
  }
  to {
    background-position: 100% 50% 0%;
  }
}
.width-setting {
  width: 100%;
}
</style>
<style>
/* modal open시 우측에 생기는 17px 공백 방지 */
.modal-open {
  padding-right: 0 !important;
}
</style>
