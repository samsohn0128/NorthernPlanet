<template>
  <span>
    <section>
      <div class="container py-4 mb-3">
        <div class="row">
          <div class="col col-lg-2"></div>
          <div
            id="make-margin"
            class="
              col-xl-4 col-lg-6
              mx-auto
              d-flex
              justify-content-center
              flex-column
              py-7
            "
          >
            <h3 class="text-center text-global">My Profile</h3>
            <div class="card-body">
              <div class="row">
                <div class="col-md-12 pt-5">
                  <label><h6 class="name-margin-setting">Email</h6></label>
                  <div class="input-group">
                    <input
                      type="email"
                      class="form-control readonly-setting"
                      :value="user.email"
                      readonly
                    />
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-md-12 pt-3">
                  <label><h6 class="name-margin-setting">Name</h6></label>
                  <div class="input-group">
                    <input
                      type="name"
                      class="form-control"
                      v-model="userName"
                      readonly
                    />
                    <!-- <div class="col-3">
                      <button
                        type="button"
                        class="btn button-setting"
                        @click="modifyName"
                      >
                        Modify
                      </button>
                    </div> -->
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-md-12 text-center mt-5">
                  <br />
                  <button
                    type="button"
                    class="btn button-danger-setting w-50"
                    data-bs-toggle="modal"
                    data-bs-target="#modal-notification"
                  >
                    Withdraw
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="col col-lg-4"></div>
        </div>
      </div>
    </section>
    <WithdrawModal></WithdrawModal>
  </span>
</template>
<script>
import WithdrawModal from './components/WithdrawModal.vue';
import { updateUserName } from '@/api/users.js';
import store from '@/store';

export default {
  name: 'MyInfo',
  components: { WithdrawModal },
  data() {
    return {
      userName: store.state.users.user.name,
    };
  },
  computed: {
    user() {
      return store.getters['users/getUser'];
    },
  },
  methods: {
    modifyName() {
      let message = '';
      if (!this.userName) {
        message = '????????? ????????? ???????????????';
        this.$toastError(message);
        return;
      } else if (!this.$nameValidate(this.userName)) {
        this.$toastError('???????????? - ??? ????????? ??? ????????????.');
        return;
      }
      let userData = {
        name: this.userName,
      };
      updateUserName(this.user.userId, userData)
        .then(({ status }) => {
          console.log(status);
          if (status != 200) {
            this.$toastError('?????? ????????? ????????? ??????????????????.');
            return;
          } else {
            this.$toastSuccess('????????? ??????????????????.');
            store.dispatch('users/SET_NAME', this.userName);
          }
        })
        .catch(() => {
          this.$toastError('?????? ?????? ????????? ??????????????????.');
        });
    },
  },
};
</script>

<style scoped>
#make-margin {
  padding-left: 100px !important;
  width: 500px;
}
.left-wing {
  display: inline-block;
  flex-direction: column;
}
.my-info-box {
  display: flex;
  justify-content: center;
}
.name-margin-setting {
  margin: 0px;
}
.readonly-setting:hover {
  cursor: default;
}
.button-setting {
  background: #66806a;
  color: white;
}
.button-danger-setting {
  background: #ba635f;
  color: white;
}
.text-global {
  color: #505753;
}
</style>
