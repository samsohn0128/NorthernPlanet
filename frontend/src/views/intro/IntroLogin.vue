<template>
  <div>
    <section>
      <div class="page-header min-vh-100">
        <div class="oblique position-absolute top-0 h-100 d-md-block d-none">
          <div
            class="
              oblique-image
              bg-cover
              position-absolute
              fixed-top
              ms-auto
              h-100
              z-index-0
              ms-n9
              intro-cover
            "
          ></div>
        </div>
        <div class="container">
          <div class="row">
            <div
              class="
                col-xl-4 col-lg-5 col-md-7
                d-flex
                flex-column
                mx-lg-8 mx-auto
              "
            >
              <div class="card card-plain">
                <div class="pb-0 text-center pb-3">
                  <h1 class="text-gradient text-focus font-weight-bolder pb-3">
                    Sign In
                  </h1>
                  <h5 class="mb-0 text-gradient text-focus">
                    Please sign in to continue using the NorthernPlanet.
                  </h5>
                </div>
                <div class="card-body">
                  <form role="form" class="mb-4" @submit.prevent="submitForm()">
                    <div class="text-center">
                      <button
                        type="button"
                        class="btn btn-lg button-setting text-lg w-80"
                        v-on:click="tryToGoogleLogIn"
                      >
                        <img
                          src="@/assets/img/logos/logo-google-thumbnail.png"
                          alt
                          height="30"
                          class="logo"
                          style="margin-right: 5%"
                        />구글 로그인
                      </button>
                    </div>
                  </form>
                </div>
                <div class="card-footer text-left pt-0 px-lg-2 px-1"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import { googleLoginUser } from '@/api/users.js';
export default {
  name: 'IntroLogin',
  components: {},
  data() {
    return {};
  },
  computed: {},
  methods: {
    async tryToGoogleLogIn() {
      try {
        await googleLoginUser();
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>
<style scoped>
.text-gradient {
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  position: relative;
  z-index: 1;
}
.text-gradient.text-focus {
  background-image: linear-gradient(310deg, #141727, #5d918a);
}
.intro-cover {
  background: linear-gradient(90deg, #98ddc1, white);
}
.button-setting {
  border: 2px solid #66806a;
  color: #456357;
}
</style>
