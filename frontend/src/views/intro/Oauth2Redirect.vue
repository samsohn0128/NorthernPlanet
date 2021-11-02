<template>
  <div>로그인중입니다.</div>
</template>

<script>
import { getUser } from '@/api/users.js';
import store from '@/store';
export default {
  name: 'Oauth2Redirect',
  data() {
    return {};
  },
  components: {},
  methods: {},
  mounted() {
    const url = this.$route.fullPath;
    console.log('url: ', url);
    const token = url.split('token=')[1];

    if (token) {
      getUser()
        .then(({ status, data }) => {
          if (status == 200) {
            let user = {
              userId: data.userId,
              email: data.email,
              name: data.name,
              image: data.image,
              oauthId: data.oauthId,
              token: token,
            };

            store.commit('users/SET_USER', user);

            this.$router.push({ name: 'RoomDashboard' });
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
};
</script>
