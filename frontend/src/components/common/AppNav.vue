<template>
  <!-- Navbar Dark -->
  <nav class="nav col-12 navbar navbar-expand-lg z-index-3 py-0">
    <div class="navbar-texts mx-0">
      <div class="col-lg-1 col-2 left-nav">
        <router-link to="/dashboard">
          <h5 class="profile-setting">NorthernPlanet</h5>
        </router-link>
      </div>
      <div class="col-md-6 col-lg-9 col-6"></div>
      <div class="col-md-2 col-lg-1 col-2 right-nav">
        <router-link to="/mypage">
          <h5 class="profile-setting">My Page</h5></router-link
        >
      </div>
      <div class="col-md-2 col-lg-1 col-2 right-nav">
        <router-link to="/" @click.native="userLogout()"
          ><h5 class="profile-setting">Logout</h5></router-link
        >
      </div>
    </div>
  </nav>
  <!-- End Navbar -->
</template>

<script>
export default {
  name: 'AppNav',
  computed: {},

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
  width: 100%;
  height: 45px;
  background: linear-gradient(
    90deg,
    rgb(119, 223, 222) 0%,
    rgb(219, 214, 74) 100%
  );
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
  color: white;
  margin: 0px;
}
</style>
