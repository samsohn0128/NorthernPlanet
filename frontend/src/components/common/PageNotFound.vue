<template>
  <div class="page-setting">
    <br />
    <h2>유효하지 않은 주소입니다.</h2>
    <br />
    <br />
    <h4>이전 페이지로 가려면</h4>
    <h4>아래의 버튼을 눌러주세요.</h4>
    <br />
    <router-link to="/dashboard">
      <h5 class="profile-setting">NorthernPlanet</h5>
    </router-link>
    <br />
    <button class="bg-green get-input join-button-setting" @click="goBack()">
      뒤로 가기
    </button>
  </div>
</template>

<script>
export default {
  name: 'PageNotFound',
  methods: {
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
.page-setting {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.get-input {
  border-width: 0px;
  border-radius: 5px;
  width: 80%;
  height: 40px;
  margin-bottom: 20px;
  padding: 0px 2px;
}
.bg-green {
  background: #303f9f;
  border-width: 0px;
  border-radius: 5px;
  color: white;
  /* color: #99a9bf; */
}
.join-button-setting {
  margin-top: 30px;
  height: 45px;
  width: 80%;
  cursor: pointer;
}
</style>
