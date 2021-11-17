<template>
  <!-- Navbar Dark -->
  <nav class="nav col-12 navbar navbar-expand-lg z-index-3 py-0">
    <div class="navbar-texts mx-0 row">
      <div class="col col-md-1 left-nav">
        <router-link to="/dashboard">
          <!-- <h5 class="profile-setting">NorthernPlanet</h5> -->
          <img
            src="@/assets/img/logos/northernplanet_logo.png"
            alt=""
            class="profile-setting"
          />
        </router-link>
      </div>
      <div class="col col-md-10"></div>
      <div class="col-md-1 right-nav profile-image">
        <div class="row dropdown">
          <div
            class="col"
            type="button"
            id="dropdownMenuButton"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            <img :src="userImage" alt class="image-user rounded-circle" />
          </div>
          <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <li v-if="user.image">
              <a class="dropdown-item" href="/mypage">My Page</a>
            </li>
            <li v-if="user.image">
              <a class="dropdown-item" href="/" @click="userLogout()">Logout</a>
            </li>
            <li v-if="!user.image">
              <a class="dropdown-item" href="/login">Login</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </nav>
  <!-- End Navbar -->
</template>

<script>
import store from '@/store';
export default {
  name: 'AppNav',
  computed: {
    user() {
      return store.getters['users/getUser'];
    },
    userImage() {
      return this.user.image
        ? this.user.image
        : require('@/assets/img/logos/focus_logo.png');
    },
  },

  methods: {
    userLogout() {
      this.$toastSuccess('안녕히 가세요');
      this.$store.commit('users/SET_LOGOUT');
      console.log('logout');
      // this.$router.push('/', () => {});
    },
  },
};
</script>

<style scoped>
.nav {
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 45px;
  background: linear-gradient(90deg, #6d9e8a, #6d9e8a);
}
.navbar-texts {
  display: flex;
  flex-flow: row;
  width: 100%;
}
.left-nav {
  text-align: center;
  align-items: center;
}
.right-nav {
  text-align: center;
  align-items: center;
}
.profile-setting {
  /* color: white;
  margin: 0px; */
  width: 160px;
  /* display: inline-block; */
}
.image-user {
  width: 30%;
  border: 1.5px solid white;
}
.profile-image {
  align-items: center;
}
</style>
