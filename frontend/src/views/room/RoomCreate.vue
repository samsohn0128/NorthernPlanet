<template>
  <section>
    <div class="container py-4 mb-5">
      <div class="row">
        <div
          class="
            col-xl-7 col-lg-10
            mx-auto
            d-flex
            justify-content-center
            flex-column
          "
        >
          <h3 class="text-center">Create new room</h3>
          <form role="form" id="contact-form" method="post" autocomplete="off">
            <div class="card-body">
              <div class="row">
                <div class="col-md-7">
                  <label><h6>Room name</h6></label>
                  <div class="input-group mb-4">
                    <input
                      v-model="roomName"
                      class="form-control"
                      type="text"
                      placeholder="멋진 이름을 지어주세요."
                    />
                  </div>
                </div>
                <div class="col-md-3 ps-4">
                  <label><h6>Start Time</h6></label>
                  <div>
                    <date-picker
                      v-model="datetime"
                      type="datetime"
                      format="YYYY-MM-DD HH:mm"
                      :placeholder="nowDateTime"
                      :disabled-date="disabledBeforeDate"
                      :disabled-time="disabledBeforeTime"
                    ></date-picker>
                  </div>
                </div>
              </div>
              <div class="form-group mb-4">
                <label><h6>Description</h6></label>
                <textarea
                  v-model="description"
                  type="text"
                  class="form-control"
                  rows="1"
                  placeholder="방을 소개해주세요."
                ></textarea>
              </div>

              <div class="form-group mb-4">
                <label><h6>Participant List</h6></label>
                <popper
                  ref="popper"
                  :prevent-default="true"
                  :force-show="true"
                  :options="{
                    placement: 'top',
                    modifiers: { offset: { offset: '0,10px' } },
                  }"
                >
                  <div class="popper">참가자 이메일을 추가해주세요.</div>

                  <span slot="reference" disable>
                    <i class="far fa-question-circle"></i>
                  </span>
                </popper>
                <div class="row">
                  <div class="col-md-10">
                    <input
                      class="form-control"
                      type="text"
                      placeholder="이메일을 검색하세요."
                      v-model="participantAccount"
                    />
                  </div>

                  <div class="col-md-2">
                    <button
                      type="button"
                      class="btn button-color-setting ms-2"
                      @click="addParticipant"
                    >
                      Add
                    </button>
                  </div>
                </div>
              </div>
              <div class="row p-3">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th class="ps-3" scope="col">Num</th>
                      <th class="ps-3" scope="col">Name</th>
                      <th class="ps-3" scope="col">Email</th>
                      <th class="ps-3" scope="col">Role</th>
                      <th class="ps-3" scope="col"></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      v-for="(participant, index) in getParticipants"
                      :key="index"
                    >
                      <th class="ps-3" scope="row">{{ index + 1 }}</th>
                      <td class="ps-3">{{ participant.name }}</td>
                      <td class="ps-3">{{ participant.email }}</td>
                      <td class="ps-3">{{ participant.code.codeName }}</td>
                      <td>
                        <div v-if="index > 0">
                          <button
                            class="btn btn-outline-danger text-danger py-1"
                            type="button"
                            id="btn-delete"
                            @click="deleteParticipant(participant.email)"
                          >
                            Delete
                          </button>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <div class="col-md-12 text-center">
                  <button
                    type="button"
                    class="btn button-create-setting w-40"
                    @click="createHandler"
                  >
                    Create Room
                  </button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
import { createRoom } from '@/api/rooms.js';
import { findUser } from '@/api/users.js';
import moment from 'moment';
import store from '@/store';
import Popper from 'vue-popperjs';
import 'vue-popperjs/dist/vue-popper.css';

export default {
  name: 'RoomCreate',
  components: { DatePicker, Popper },
  data() {
    return {
      datetime: '',
      roomName: '',
      description: '',
      participants: [
        {
          name: store.getters['users/getUser'].name,
          email: store.getters['users/getUser'].email,
          code: {
            codeId: '001',
            codeName: 'Manager',
          },
        },
      ],
      participant: '',
      participantAccount: '',
      nowDateTime: moment(new Date()).format('YYYY-MM-DD HH:mm'),
    };
  },
  computed: {
    getParticipants() {
      return this.participants;
    },
    user() {
      return store.getters['users/getUser'];
    },
  },
  methods: {
    addParticipant() {
      let msg = '';
      if (!this.participantAccount) {
        msg = '추가하려는 사용자 이메일을 입력해주세요';
        this.$toastError(msg);
        return;
      }

      // 같은 계정 참가자 추가 안되게 함.
      const size = this.participants.length;

      for (let i = 0; i < size; i++) {
        if (this.participants[i].email == this.participantAccount) {
          this.$toastError('이미 등록된 사용자입니다.');
          return;
        }
      }

      this.getUsername().then(() => {
        if (!this.participants) {
          this.$toastError('사용자 계정이 없습니다.');
        } else {
          this.participants.push({
            name: this.participant.data.name,
            email: this.participantAccount,
            code: {
              codeId: '002',
              codeName: 'Presenter',
            },
          });
        }
      });
    },

    deleteParticipant(email) {
      this.participants.forEach((element, index) => {
        if (element.email == email) {
          this.participants.splice(index);
        }
      });
    },

    createHandler() {
      let msg = '';
      let err = false;

      if (!this.roomName) {
        msg = '방 이름을 입력해주세요';
        err = true;
      } else if (!this.description) {
        msg = '방 설명을 입력해주세요';
        err = true;
      }

      if (err) {
        this.$toastError(msg);
        return;
      } else {
        let roomData = {
          name: this.roomName,
          description: this.description,
          startTime: this.datetime,
          email: this.user.email,
          participants: this.participants,
        };

        createRoom(roomData)
          .then(({ status }) => {
            if (status != 200) {
              this.$toastError('방 생성 실패했습니다.');
              return;
            } else {
              this.$toastSuccess('방이 생성됐습니다.');
              this.$router.push('/dashboard');
            }
          })
          .catch(() => {
            this.$toastError('error! catch');
          });
      }
    },

    async getUsername() {
      try {
        this.participant = await findUser(this.participantAccount);
      } catch (err) {
        this.$toastError('사용자 확인이 실패했습니다..: ' + err);
      }
    },

    disabledBeforeDate(date) {
      return (
        date <
        moment(
          `${new Date().getDate()}-${
            new Date().getMonth() + 1
          }-${new Date().getFullYear()}`,
          'DD-MM-YYYY',
        )
      );
    },
    disabledBeforeTime(time) {
      return (
        time <
        moment(
          `${new Date().getHours() - 1}:${new Date().getMinutes()}`,
          'HH:mm',
        )
      );
    },
    // inputChanged() {
    //   this.$store.dispatch('users/GET_ALL_USERS', this.participantAccount);
    // },
  },
};
</script>
<style scoped>
.button-color-setting {
  background: #66806a;
  color: white;
}
.button-create-setting {
  background: linear-gradient(90deg, #77dfde, #dbd64a);
  color: white;
}
</style>
