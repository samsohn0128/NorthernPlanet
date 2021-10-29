<template>
  <div class="main-square">
    <ul class="list-group">
      <li class="list-group-item square">
        <router-link to="/mypage" class="router">내 정보</router-link>
      </li>
      <li class="list-group-item square">
        <router-link to="/mypage/presentation" class="router"
          >내 발표자료</router-link
        >
      </li>
      <!-- <li class="list-group-item square">공유된 발표자료</li> -->
    </ul>
  </div>
</template>

<script>
export default {
  name: 'MyPageMenu',
};
</script>

<style scoped>
.main-square {
  width: 200px;
  height: 700px;
  text-align: center;
  border-style: solid;
  border-width: 1.5px;
  border-color: black;
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
</style>
