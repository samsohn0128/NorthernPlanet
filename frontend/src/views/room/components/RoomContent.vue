<template>
  <section>
    <form role="form" id="contact-form" method="post" autocomplete="off">
      <div class="card-body">
        <div class="row">
          <div class="col-md-7">
            <label class="text-global">
              <h6>Room Name</h6>
            </label>
            <div class="input-group mb-3">
              <input
                v-model="roomName"
                class="form-control"
                type="text"
                placeholder="멋진 이름을 지어주세요."
                :readonly="!isManager"
              />
            </div>
          </div>
          <div class="col-md-3 ps-3">
            <label>
              <h6>Start Time</h6>
            </label>
            <div>
              <date-picker
                v-model="datetime"
                type="datetime"
                :placeholder="datetime"
                format="YYYY-MM-DD HH:mm"
                :disabled-date="disabledBeforeDate"
                :disabled-time="disabledBeforeTime"
                :editable="isManager"
              ></date-picker>
            </div>
          </div>
        </div>
        <div class="form-group mb-4">
          <label>
            <h6>Description</h6>
          </label>
          <textarea
            v-model="description"
            type="text"
            class="form-control"
            rows="1"
            placeholder="방을 소개해주세요."
            :readonly="!isManager"
          ></textarea>
        </div>
        <label>
          <h6>Participant List</h6>
        </label>
        <div class="form-group mb-4" v-if="isManager">
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
                class="btn button-color-setting"
                @click="addParticipant"
              >
                Add
              </button>
            </div>
          </div>
        </div>

        <div class="mb-4 row px-3">
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
              <tr v-for="(participant, index) in getParticipants" :key="index">
                <th scope="row" class="ps-3">{{ index + 1 }}</th>
                <td class="ps-3">{{ participant.name }}</td>
                <td class="ps-3">{{ participant.email }}</td>
                <td class="ps-3">{{ participant.code.codeName }}</td>
                <td>
                  <div v-if="index > 0 && isManager">
                    <button
                      class="btn button-danger-setting py-1"
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

          <div class="room-button-setting" v-if="isManager">
            <span>
              <button
                type="button"
                class="button-setting button-color-setting btn bg-gradient-dark"
                @click="updateHandler"
              >
                Update Room
              </button>
            </span>
            <span>
              <button
                type="button"
                class="button-setting btn button-danger-setting"
                data-bs-toggle="modal"
                data-bs-target="#modal-notification"
              >
                Delete Room
              </button>
            </span>
            <span>
              <button
                type="button"
                class="button-setting btn button-create-setting"
                data-bs-toggle="modal"
                data-bs-target="#modal-link"
              >
                make link
              </button>
            </span>
          </div>
        </div>
        <!-- <h3 class="text-center">Presentation Files</h3>

        <div v-if="files.length == 0 && !this.$store.state.rooms.room.endTime">
          <UploadDialog />
        </div>
        <div v-else>
          <RoomFiledetail></RoomFiledetail>
          <div class="text-center">
            <button
              class="btn btn-outline-danger text-danger"
              type="button"
              id="btn-delete"
              @click="deletefile()"
            >
              발표 자료 삭제
            </button>
          </div>
        </div> -->
      </div>
    </form>
    <RoomDeleteModal v-bind:roomId="this.room.roomId"></RoomDeleteModal>
    <LinkModal v-bind:roomId="this.room.roomId"></LinkModal>
  </section>
</template>

<script>
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
import { updateRoom } from '@/api/rooms.js';
import { findUser } from '@/api/users.js';
//import { deletetot, showfiledetail } from '@/api/file.js';
import moment from 'moment';
import RoomDeleteModal from './RoomDeleteModal.vue';
import LinkModal from './LinkModal.vue';
// import UploadDialog from './UploadDialog';
// import RoomFiledetail from './RoomFiledetail.vue';
import store from '@/store';
export default {
  name: 'RoomContent',
  components: {
    DatePicker,
    RoomDeleteModal,
    LinkModal,
    // UploadDialog,
    // RoomFiledetail,
  },
  data() {
    return {
      datetime: store.getters['rooms/room'].startTime,
      roomName: store.getters['rooms/room'].name,
      description: store.getters['rooms/room'].description,
      participants: store.getters['rooms/room'].participants,
      participant: '',
      participantAccount: '',
      nowDateTime: moment(new Date()).format('YYYY-MM-DD HH:mm'),
      isManager: false,
      files: [],
    };
  },
  created() {
    window.scrollTo(0, 0);
    //사용자가 방장이고 방이 종료되지 않았을 때
    if (this.room.managerId == this.user.userId && !this.room.endTime) {
      this.isManager = true;
    }
    console.log('am I manager? ', this.isManager);
  },
  computed: {
    getParticipants() {
      return this.participants;
    },
    user() {
      return store.getters['users/getUser'];
    },
    room() {
      return store.getters['rooms/room'];
    },
  },
  methods: {
    addParticipant() {
      let msg = '';
      if (!this.participantAccount) {
        msg = '추가하려는 사용자 이메일 및 역할을 입력해주세요';
        this.$toastError(msg);
        return;
      }

      // 같은 계정 참가자 추가 안되게 함.
      const size = this.participants.length;

      for (let i = 0; i < size; i++) {
        if (this.participants[i].email == this.participantAccount) {
          this.$$toastError('이미 등록된 사용자입니다.');
          return;
        }
      }

      this.getUsername().then(() => {
        if (!this.participants) {
          this.$toastError('사용자 게정이 없습니다.');
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

    updateHandler() {
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
          userId: this.user.userId,
          roomId: this.room.roomId,
          name: this.roomName,
          description: this.description,
          startTime: this.datetime,
          participants: this.participants,
        };

        updateRoom(roomData)
          .then(({ status }) => {
            if (status != 200) {
              this.$toastError('방 정보 수정중 실패했습니다.');
              return;
            } else {
              this.$toastSuccess('방 정보가 수정됐습니다.');
              store.dispatch('rooms/setRoom', roomData);
            }
          })
          .catch(() => {
            this.$toastError('방 정보 수정 실패했습니다.');
          });
      }
    },

    async getUsername() {
      try {
        this.participant = await findUser(this.participantAccount);
      } catch (err) {
        this.$toastError('사용자 확인이 실패했습니다..', err);
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
    //   store.dispatch('GET_ALL_USERS', this.participantAccount);
    // },
  },
};
</script>

<style scoped>
.room-button-setting {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.button-setting {
  width: 140px;
  height: 40px;
  padding: 5px;
  margin: 5px;
}
.button-color-setting {
  background: #66806a;
  color: white;
}
.button-outline-setting {
  border: 1.5px solid #66806a;
  color: #456357;
}
.button-create-setting {
  background: #ffc286;
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
